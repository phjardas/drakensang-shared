package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.model.Advantage;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.MissingResourceException;


public final class AdvantageDao {
    private static final org.slf4j.Logger LOG =
        org.slf4j.LoggerFactory.getLogger(AdvantageDao.class);
    private static final EnumerationDao<Advantage> DAO =
        new EnumerationDao<Advantage>(Advantage.class,
            "select * from _template_advantages order by id") {
            @Override
            protected Advantage create(ResultSet result)
                throws SQLException {
                return create(result.getString("Id"), result.getString("Name"),
                    result.getString("Description"), result.getString("AttributeModifier"));
            }

            private Advantage create(String id, String nameKey, String infoKey, String modifiers) {
                try {
                    final String name = Messages.getRequired(nameKey);
                    final String info = Messages.getRequired(infoKey);

                    return new Advantage(id, name, info, modifiers);
                } catch (MissingResourceException e) {
                    LOG.debug("Skipping obsolete advantage " + id);

                    return null;
                }
            }
        };

    private AdvantageDao() {
        // utility class
    }

    public static Advantage valueOf(String id) {
        return DAO.valueOf(id);
    }

    public static Advantage[] values() {
        return DAO.values();
    }
}
