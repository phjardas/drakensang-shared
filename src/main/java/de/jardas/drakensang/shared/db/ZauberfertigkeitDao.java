package de.jardas.drakensang.shared.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.MissingResourceException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import de.jardas.drakensang.shared.DrakensangException;
import de.jardas.drakensang.shared.model.Zauberfertigkeit;

public final class ZauberfertigkeitDao {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(ZauberfertigkeitDao.class);
	private static Zauberfertigkeit[] values;
	private static String[] attributes;

	static {
		LOG.info("Loading spells");

		try {
			final PreparedStatement statement = Static.getConnection()
					.prepareStatement(
							"select * from _template_zauber order by id");
			final ResultSet result = statement.executeQuery();

			while (result.next()) {
				create(result.getString("Id"), result.getString("Name"), result
						.getString("Description"), result.getString("ZaAttr"),
						result.getString("CategoryName"), result
								.getString("ZaP1"), result.getString("ZaP2"),
						result.getString("ZaP3"));
			}
		} catch (SQLException e) {
			throw new DrakensangException("Error loading spells: " + e, e);
		}

		LOG.info("Loaded " + values.length + " spells.");
	}

	private ZauberfertigkeitDao() {
		// utility class
	}

	private static void create(String id, String nameKey, String infoKey,
			String attribute, String categoryKey, String attribute1,
			String attribute2, String attribute3) {
		try {
			Messages.getRequired(nameKey);
		} catch (MissingResourceException e) {
			LOG.debug("Skipping obsolete spell " + id);
		}

		final Zauberfertigkeit adv = new Zauberfertigkeit(id, nameKey, infoKey,
				categoryKey, attribute, toAttributes(attribute1, attribute2,
						attribute3));

		if (values == null) {
			values = new Zauberfertigkeit[] { adv, };
		} else {
			values = (Zauberfertigkeit[]) ArrayUtils.add(values, adv);
		}

		if (attributes == null) {
			attributes = new String[] { adv.getAttribute(), };
		} else {
			attributes = (String[]) ArrayUtils.add(attributes, adv
					.getAttribute());
		}

		LOG.debug("Loaded spell: {}", adv);
	}

	private static String[] toAttributes(String attribute1, String attribute2,
			String attribute3) {
		if (StringUtils.isBlank(attribute1) || StringUtils.isBlank(attribute2)
				|| StringUtils.isBlank(attribute3)) {
			return null;
		}

		return new String[] { attribute1, attribute2, attribute3 };
	}

	public static Zauberfertigkeit valueOf(String attribute) {
		for (Zauberfertigkeit adv : values) {
			if (adv.getAttribute().equals(attribute)) {
				return adv;
			}
		}

		throw new IllegalArgumentException("Unknown spell: " + attribute);
	}

	public static Zauberfertigkeit[] values() {
		return values;
	}

	public static String[] attributes() {
		return attributes;
	}
}
