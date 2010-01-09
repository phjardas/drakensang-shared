package de.jardas.drakensang.shared.db.inventory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.jardas.drakensang.shared.model.Character;
import de.jardas.drakensang.shared.model.inventory.Inventory;
import de.jardas.drakensang.shared.model.inventory.InventoryItem;

public class InventoryDao {
	private static final Set<InventoryItemDao<? extends InventoryItem>> ITEM_DAOS = new HashSet<InventoryItemDao<? extends InventoryItem>>();

	static {
		ITEM_DAOS.add(new WeaponDao());
		ITEM_DAOS.add(new ShieldDao());
		ITEM_DAOS.add(new ArmorDao());
		ITEM_DAOS.add(new AmmoDao());
		ITEM_DAOS.add(new MoneyDao());
		ITEM_DAOS.add(new ItemDao());
		ITEM_DAOS.add(new JewelryDao());
		ITEM_DAOS.add(new KeyDao());
		ITEM_DAOS.add(new RecipeDao());
		ITEM_DAOS.add(new TorchDao());
		ITEM_DAOS.add(new BookDao());
	}

	public static void loadInventory(Character character, String tablePrefix,
			Connection connection) throws SQLException {
		final List<InventoryItem> items = loadItems(character.getGuid(),
				tablePrefix, connection);

		for (InventoryItem item : items) {
			character.getInventory().addItem(item);
		}
	}

	private static List<InventoryItem> loadItems(byte[] storageGuid,
			String tablePrefix, Connection connection) {
		final List<InventoryItem> items = new ArrayList<InventoryItem>();

		for (InventoryItemDao<? extends InventoryItem> dao : ITEM_DAOS) {
			items.addAll(dao.loadItems(storageGuid, tablePrefix, connection));
		}

		return items;
	}

	public static InventoryItem loadItem(String type, String id,
			String tablePrefix, Connection connection) {
		final InventoryItemDao<? extends InventoryItem> dao = getInventoryItemDao(type);
		return dao.loadItem(id, tablePrefix, connection);
	}

	private static <I extends InventoryItem> InventoryItemDao<I> getInventoryItemDao(
			Class<I> itemClass) {
		for (InventoryItemDao<? extends InventoryItem> dao : ITEM_DAOS) {
			if (dao.isApplicable(itemClass)) {
				@SuppressWarnings("unchecked")
				final InventoryItemDao<I> theDao = (InventoryItemDao<I>) dao;

				return theDao;
			}
		}

		throw new IllegalArgumentException("No DAO known for "
				+ itemClass.getName() + ".");
	}

	private static InventoryItemDao<? extends InventoryItem> getInventoryItemDao(
			String type) {
		for (InventoryItemDao<? extends InventoryItem> dao : ITEM_DAOS) {
			if (dao.isApplicable(type)) {
				return dao;
			}
		}

		throw new IllegalArgumentException("No DAO known for type " + type
				+ ".");
	}

	public static void save(Inventory inventory, String tablePrefix,
			Connection connection) throws SQLException {
		for (InventoryItem item : inventory.getItems()) {
			getInventoryItemDao(item.getClass()).save(item, tablePrefix,
					connection);
		}
	}

	public static final class ItemIdentifier {
		private final String type;
		private final String id;

		public ItemIdentifier(String type, String id) {
			super();
			this.type = type;
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public String getId() {
			return id;
		}
	}
}
