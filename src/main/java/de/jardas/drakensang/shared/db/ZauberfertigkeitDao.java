package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.model.Zauberfertigkeit;

import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.MissingResourceException;


public final class ZauberfertigkeitDao {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(TalentDao.class);
    private static final ZauberfertigkeitDaoHelper DAO = new ZauberfertigkeitDaoHelper();

    private ZauberfertigkeitDao() {
        // utility class
    }

    public static Zauberfertigkeit valueOf(String attribute) {
        return DAO.valueOf(attribute);
    }

    public static Zauberfertigkeit[] values() {
        return DAO.values();
    }

    public static String[] attributes() {
        return DAO.getAttributes();
    }

    private static class ZauberfertigkeitDaoHelper extends EnumerationDao<Zauberfertigkeit> {
        private final String[] attributes;

        public ZauberfertigkeitDaoHelper() {
            super(Zauberfertigkeit.class, "select * from _template_zauber order by id");

            int i = 0;
            attributes = new String[values().length];

            for (Zauberfertigkeit talent : values()) {
                attributes[i++] = talent.getAttribute();
            }
        }

        @Override
        protected Zauberfertigkeit create(ResultSet result)
            throws SQLException {
            return create(result.getString("Id"), result.getString("Name"),
                result.getString("Description"), result.getString("ZaAttr"),
                result.getString("CategoryName"), result.getString("ZaP1"),
                result.getString("ZaP2"), result.getString("ZaP3"));
        }

        private Zauberfertigkeit create(String id, String nameKey, String infoKey,
            String attribute, String categoryKey, String attribute1, String attribute2,
            String attribute3) {
            try {
                Messages.getRequired(nameKey);
            } catch (MissingResourceException e) {
                LOG.debug("Skipping obsolete spell " + id);

                return null;
            }

            return new Zauberfertigkeit(id, nameKey, infoKey, categoryKey, attribute,
                toAttributes(attribute1, attribute2, attribute3));
        }

        private String[] toAttributes(String attribute1, String attribute2, String attribute3) {
            if (StringUtils.isBlank(attribute1) ||
                    StringUtils.isBlank(attribute2) ||
                    StringUtils.isBlank(attribute3)) {
                return null;
            }

            return new String[] { attribute1, attribute2, attribute3 };
        }

        public String[] getAttributes() {
            return attributes;
        }

        protected boolean isItem(Zauberfertigkeit item, String id) {
            return item.getAttribute().equals(id);
        }
    }
}
