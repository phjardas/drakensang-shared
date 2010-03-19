package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.model.Sonderfertigkeit;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.MissingResourceException;


public final class SonderfertigkeitDao {
    private static final org.slf4j.Logger LOG =
        org.slf4j.LoggerFactory.getLogger(SonderfertigkeitDao.class);
    private static final EnumerationDao<Sonderfertigkeit> DAO =
        new EnumerationDao<Sonderfertigkeit>(Sonderfertigkeit.class,
            "select * from _template_attacks order by id") {
            @Override
            protected Sonderfertigkeit create(ResultSet result)
                throws SQLException {
                return create(result.getString("Id"), result.getString("Name"),
                    result.getString("Description"), result.getString("AtAttr"),
                    result.getString("AttrCategory"));
            }

            private Sonderfertigkeit create(String id, String nameKey, String infoKey,
                String attribute, String categoryKey) {
                if ("None".equals(categoryKey)) {
                    LOG.debug("Skipping unavailable attack " + id);

                    return null;
                }

                try {
                    final String name = Messages.getRequired(nameKey);
                    final String info = Messages.getRequired(infoKey);

                    return new Sonderfertigkeit(id, name, info, attribute, categoryKey);
                } catch (MissingResourceException e) {
                    LOG.debug("Skipping obsolete attack " + id);

                    return null;
                }
            }

            protected boolean isItem(Sonderfertigkeit item, String id) {
                return item.getAttribute().equals(id);
            }
        };

    private SonderfertigkeitDao() {
        // utility class
    }

    public static Sonderfertigkeit valueOf(String attribute) {
        return DAO.valueOf(attribute);
    }

    public static Sonderfertigkeit[] values() {
        return DAO.values();
    }
}
