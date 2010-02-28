package de.jardas.drakensang.shared.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.MissingResourceException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import de.jardas.drakensang.shared.DrakensangException;
import de.jardas.drakensang.shared.model.Talent;

public final class TalentDao {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(TalentDao.class);
	private static Talent[] values;
	private static String[] attributes;

	static {
		LOG.info("Loading talents");

		try {
			final PreparedStatement statement = Static.getConnection()
					.prepareStatement(
							"select * from _template_talent order by id");
			final ResultSet result = statement.executeQuery();

			while (result.next()) {
				create(result.getString("Id"), result.getString("Name"), result
						.getString("Description"), result.getString("TaAttr"),
						result.getString("TaCategory"), result
								.getString("TaGroup"),
						result.getString("TaP1"), result.getString("TaP2"),
						result.getString("TaP3"));
			}
		} catch (SQLException e) {
			throw new DrakensangException("Error loading talents: " + e, e);
		}

		LOG.info("Loaded " + values.length + " talents.");
	}

	private TalentDao() {
		// utility class
	}

	private static void create(String id, String nameKey, String infoKey,
			String attribute, String categoryKey, String groupKey,
			String attribute1, String attribute2, String attribute3) {
		try {
			Messages.getRequired(nameKey);
		} catch (MissingResourceException e) {
			LOG.debug("Skipping obsolete talent " + id);
		}

		final Talent adv = new Talent(id, nameKey, infoKey, attribute,
				categoryKey, groupKey, toAttributes(attribute1, attribute2,
						attribute3));

		if (values == null) {
			values = new Talent[] { adv, };
		} else {
			values = (Talent[]) ArrayUtils.add(values, adv);
		}

		if (attributes == null) {
			attributes = new String[] { adv.getAttribute(), };
		} else {
			attributes = (String[]) ArrayUtils.add(attributes, adv
					.getAttribute());
		}

		LOG.debug("Loaded talent: {}", adv);
	}

	private static String[] toAttributes(String attribute1, String attribute2,
			String attribute3) {
		if (StringUtils.isBlank(attribute1) || StringUtils.isBlank(attribute2)
				|| StringUtils.isBlank(attribute3)) {
			return null;
		}

		return new String[] { attribute1, attribute2, attribute3 };
	}

	public static Talent valueOf(String attribute) {
		for (Talent adv : values) {
			if (adv.getAttribute().equals(attribute)) {
				return adv;
			}
		}

		throw new IllegalArgumentException("Unknown talent: " + attribute);
	}

	public static Talent[] values() {
		return values;
	}

	public static String[] attributes() {
		return attributes;
	}
}
