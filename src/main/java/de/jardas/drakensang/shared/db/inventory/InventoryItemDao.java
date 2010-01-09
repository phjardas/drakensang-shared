package de.jardas.drakensang.shared.db.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import de.jardas.drakensang.shared.DrakensangException;
import de.jardas.drakensang.shared.db.Static;
import de.jardas.drakensang.shared.db.UpdateStatementBuilder;
import de.jardas.drakensang.shared.db.UpdateStatementBuilder.ParameterType;
import de.jardas.drakensang.shared.model.inventory.Ammo;
import de.jardas.drakensang.shared.model.inventory.Armor;
import de.jardas.drakensang.shared.model.inventory.Book;
import de.jardas.drakensang.shared.model.inventory.EquipableItem;
import de.jardas.drakensang.shared.model.inventory.EquipmentSlot;
import de.jardas.drakensang.shared.model.inventory.InventoryItem;
import de.jardas.drakensang.shared.model.inventory.Jewelry;
import de.jardas.drakensang.shared.model.inventory.Key;
import de.jardas.drakensang.shared.model.inventory.Money;
import de.jardas.drakensang.shared.model.inventory.Recipe;
import de.jardas.drakensang.shared.model.inventory.Shield;
import de.jardas.drakensang.shared.model.inventory.Torch;
import de.jardas.drakensang.shared.model.inventory.Weapon;

public abstract class InventoryItemDao<I extends InventoryItem> {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(InventoryItemDao.class);
	private final Collection<I> templates = new ArrayList<I>();
	private final Class<I> itemClass;
	private final String table;

	public InventoryItemDao(Class<I> itemClass, String table) {
		super();
		this.itemClass = itemClass;
		this.table = table;
	}

	public Class<I> getItemClass() {
		return this.itemClass;
	}

	public boolean isApplicable(Class<? extends InventoryItem> clazz) {
		return getItemClass().isAssignableFrom(clazz);
	}

	public boolean isApplicable(String type) {
		return table.equalsIgnoreCase(type);
	}

	public I load(ResultSet results) {
		I item;

		try {
			item = getItemClass().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error creating inventory item of type "
					+ getItemClass().getName() + ": " + e, e);
		}

		try {
			try {
				item.setGuid(results.getBytes("Guid"));
			} catch (SQLException e) {
				// ignore
			}

			item.setId(results.getString("Id"));
			item.setName(results.getString("Name"));
			item.setIcon(results.getString("IconBrush"));

			if (item instanceof EquipableItem) {
				final EquipableItem equipable = (EquipableItem) item;

				try {
					final String slot = results.getString("EquipmentSlotID");

					if (StringUtils.isNotEmpty(slot)) {
						equipable.setSlot(EquipmentSlot.valueOf(results
								.getString("EquipmentSlotID")));
					}
				} catch (SQLException e) {
					// ignore
				}
			}

			if (!Money.class.isAssignableFrom(getItemClass())) {
				item.setWeight(results.getInt("Gew"));
				item.setValue(results.getInt("Value"));

				if (hasQuestId()) {
					item.setQuestId(results.getString("QuestId"));
				}

				item.setCanUse(results.getBoolean("CanUse"));

				try {
					item.setCanDestroy(results.getBoolean("CanDestroy"));
				} catch (SQLException e) {
					// ignore
				}

				try {
					item.setUseTalent(results.getString("UseTalent"));
				} catch (SQLException e) {
					// ignore
				}
			}

			if (hasQuestId()) {
				item.setQuestItem((results.getString("QuestId") != null)
						&& !"NONE".equalsIgnoreCase(results
								.getString("QuestId")));
			}

			if (hasTaBonus()) {
				item.setTaBonus(results.getInt("TaBonus"));
			}

			if (item.isCountable()) {
				item.setCount(results.getInt("StackCount"));
				item.setMaxCount(results.getInt("MaxStackCount"));
			}

			item.setPickingRange(results.getInt("PickingRange"));
			item.setGraphics(results.getString("Graphics"));
			item.setPhysics(results.getString("Physics"));
			item.setLookAtText(results.getString("LookAtText"));

			try {
				item.setStorageGuid(results.getBytes("StorageGuid"));
			} catch (SQLException e) {
				// ignore
			}

			return item;
		} catch (SQLException e) {
			throw new DrakensangException("Error loading inventory item "
					+ getItemClass().getName() + ": " + e);
		}
	}

	protected boolean hasQuestId() {
		return !Recipe.class.isAssignableFrom(getItemClass())
				&& !Money.class.isAssignableFrom(getItemClass())
				&& !Ammo.class.isAssignableFrom(getItemClass());
	}

	protected boolean hasTaBonus() {
		return !Book.class.isAssignableFrom(getItemClass())
				&& !Armor.class.isAssignableFrom(getItemClass())
				&& !Weapon.class.isAssignableFrom(getItemClass())
				&& !Money.class.isAssignableFrom(getItemClass())
				&& !Jewelry.class.isAssignableFrom(getItemClass())
				&& !Key.class.isAssignableFrom(getItemClass())
				&& !Recipe.class.isAssignableFrom(getItemClass())
				&& !Shield.class.isAssignableFrom(getItemClass())
				&& !Ammo.class.isAssignableFrom(getItemClass())
				&& !Torch.class.isAssignableFrom(getItemClass());
	}

	public Collection<InventoryItem> loadItems(byte[] storageGuid,
			String tablePrefix, Connection connection) {
		final String sql = "select * from " + tablePrefix + table
				+ " where StorageGuid = ?";
		LOG.debug("Loading inventory for storage Guid "
				+ Arrays.toString(storageGuid) + ": " + sql);

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setBytes(1, storageGuid);

			ResultSet results = stmt.executeQuery();
			List<InventoryItem> items = new ArrayList<InventoryItem>();

			while (results.next()) {
				try {
					items.add(load(results));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			return items;
		} catch (SQLException e) {
			throw new DrakensangException("Error loading items of type "
					+ itemClass.getName() + " in storage Guid "
					+ Arrays.toString(storageGuid) + ": " + e, e);
		}
	}

	public I loadItem(String id, String tablePrefix, Connection connection) {
		try {
			final PreparedStatement stmt = connection
					.prepareStatement("select * from " + tablePrefix + table
							+ " where Id = ?");
			stmt.setString(1, id);

			final ResultSet results = stmt.executeQuery();

			if (!results.next()) {
				throw new DrakensangException("No item of type "
						+ itemClass.getName() + " known with id " + id);
			}
			try {
				return load(results);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} catch (SQLException e) {
			throw new DrakensangException("Error loading item of type "
					+ itemClass.getName() + " with id " + id + ": " + e, e);
		}
	}

	public void save(InventoryItem item, String tablePrefix,
			Connection connection) throws SQLException {
		UpdateStatementBuilder builder = new UpdateStatementBuilder(tablePrefix
				+ table, "guid = ?");

		@SuppressWarnings("unchecked")
		final I theItem = (I) item;
		appendUpdateStatements(builder, theItem);

		builder.addParameter(ParameterType.Bytes, item.getGuid());
		LOG.debug("Inventory update: {}", builder);

		builder.createStatement(connection).executeUpdate();
	}

	protected void appendUpdateStatements(UpdateStatementBuilder builder, I item) {
		builder.append("Name = ?", item.getName());
		builder.append("Id = ?", item.getId());

		if (item.isCountable()) {
			builder
					.append("StackCount = ?", ParameterType.Int, item
							.getCount());
		}
	}

	public Collection<I> getTemplates() throws SQLException {
		if (!templates.isEmpty()) {
			return templates;
		}

		final ResultSet results = Static.getConnection().prepareStatement(
				"select Id from _Template_" + table).executeQuery();

		while (results.next()) {
			templates.add(loadItem(results.getString("Id"), "_Template_",
					Static.getConnection()));
		}

		return templates;
	}
}
