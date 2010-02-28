package de.jardas.drakensang.shared.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.MissingResourceException;

import org.apache.commons.lang.ArrayUtils;

import de.jardas.drakensang.shared.DrakensangException;
import de.jardas.drakensang.shared.model.Sonderfertigkeit;

public final class SonderfertigkeitDao {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(SonderfertigkeitDao.class);
	private static Sonderfertigkeit[] values;

	static {
		LOG.info("Loading attacks");

		try {
			final PreparedStatement statement = Static.getConnection()
					.prepareStatement(
							"select * from _template_attacks order by id");
			final ResultSet result = statement.executeQuery();

			while (result.next()) {
				create(result.getString("Id"), result.getString("Name"), result
						.getString("Description"), result.getString("AtAttr"),
						result.getString("AttrCategory"));
			}
		} catch (SQLException e) {
			throw new DrakensangException("Error loading attacks: " + e, e);
		}

		LOG.info("Loaded " + values.length + " attacks.");
	}

	private SonderfertigkeitDao() {
		// utility class
	}

	private static void create(String id, String nameKey, String infoKey,
			String attribute, String categoryKey) {
		if ("None".equals(categoryKey)) {
			LOG.debug("Skipping unavailable attack " + id);
			return;
		}

		try {
			final String name = Messages.getRequired(nameKey);
			final String info = Messages.getRequired(infoKey);
			final Sonderfertigkeit adv = new Sonderfertigkeit(id, name, info,
					attribute, categoryKey);

			if (values == null) {
				values = new Sonderfertigkeit[] { adv, };
			} else {
				values = (Sonderfertigkeit[]) ArrayUtils.add(values, adv);
			}

			LOG.debug("Loaded attack: {}", adv);
		} catch (MissingResourceException e) {
			LOG.debug("Skipping obsolete attack " + id);
		}
	}

	public static Sonderfertigkeit valueOf(String id) {
		for (Sonderfertigkeit adv : values) {
			if (adv.getAttribute().equals(id)) {
				return adv;
			}
		}

		throw new IllegalArgumentException("Unknown attack: " + id);
	}

	public static Sonderfertigkeit[] values() {
		return values;
	}
}
