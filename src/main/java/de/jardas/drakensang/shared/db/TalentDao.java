package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.model.Talent;

import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.MissingResourceException;


public final class TalentDao {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(TalentDao.class);
    private static final TalentDaoHelper DAO = new TalentDaoHelper();

    private TalentDao() {
        // utility class
    }

    public static Talent valueOf(String attribute) {
        return DAO.valueOf(attribute);
    }

    public static Talent[] values() {
        return DAO.values();
    }

    public static String[] attributes() {
        return DAO.getAttributes();
    }

    private static class TalentDaoHelper extends EnumerationDao<Talent> {
        private final String[] attributes;

        public TalentDaoHelper() {
            super(Talent.class, "select * from _template_talent order by id");

            int i = 0;
            attributes = new String[values().length];

            for (Talent talent : values()) {
                attributes[i++] = talent.getAttribute();
            }
        }

        @Override
        protected Talent create(ResultSet result) throws SQLException {
            return create(result.getString("Id"), result.getString("Name"),
                result.getString("Description"), result.getString("TaAttr"),
                result.getString("TaCategory"), result.getString("TaGroup"),
                result.getString("TaP1"), result.getString("TaP2"), result.getString("TaP3"));
        }

        private Talent create(String id, String nameKey, String infoKey, String attribute,
            String categoryKey, String groupKey, String attribute1, String attribute2,
            String attribute3) {
            try {
                Messages.getRequired(nameKey);
            } catch (MissingResourceException e) {
                LOG.debug("Skipping obsolete talent " + id);
            }

            final Talent talent =
                new Talent(id, nameKey, infoKey, attribute, categoryKey, groupKey,
                    toAttributes(attribute1, attribute2, attribute3));

            return talent;
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

        protected boolean isItem(Talent item, String id) {
            return item.getAttribute().equals(id);
        }
    }
}
