package de.jardas.drakensang.shared.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.MissingResourceException;

import org.apache.commons.lang.ArrayUtils;

import de.jardas.drakensang.shared.DrakensangException;
import de.jardas.drakensang.shared.model.Advantage;

public final class AdvantageDao {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(AdvantageDao.class);
	private static Advantage[] values;

	static {
		LOG.info("Loading advantages");

		try {
			final PreparedStatement statement = Static.getConnection()
					.prepareStatement(
							"select * from _template_advantages order by id");
			final ResultSet result = statement.executeQuery();

			while (result.next()) {
				create(result.getString("Id"), result.getString("Name"), result
						.getString("Description"), result
						.getString("AttributeModifier"));
			}
		} catch (SQLException e) {
			throw new DrakensangException("Error loading advantages: " + e, e);
		}

		LOG.info("Loaded " + values.length + " advantages.");
	}

	private AdvantageDao() {
		// utility class
	}

	private static void create(String id, String nameKey, String infoKey,
			String modifiers) {
		try {
			final String name = Messages.getRequired(nameKey);
			final String info = Messages.getRequired(infoKey);

			final Advantage adv = new Advantage(id, name, info, modifiers);

			if (values == null) {
				values = new Advantage[] { adv, };
			} else {
				values = (Advantage[]) ArrayUtils.add(values, adv);
			}

			LOG.debug("Loaded advantage: {}", adv);
		} catch (MissingResourceException e) {
			LOG.debug("Skipping obsolete advantage " + id);
		}
	}

	public static Advantage valueOf(String id) {
		for (Advantage adv : values) {
			if (adv.getId().equals(id)) {
				return adv;
			}
		}

		throw new IllegalArgumentException("Unknown advantage: " + id);
	}

	public static Advantage[] values() {
		return values;
	}
}
