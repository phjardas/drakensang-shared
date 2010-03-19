package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.model.Culture;
import de.jardas.drakensang.shared.model.Identified;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CultureDao {
    private static final CultureDaoHelper DAO =
        new CultureDaoHelper(Culture.class,
            "select Id, LEMax, AUMax, AEMax, MR from _Template_culture order by Id");

    private CultureDao() {
        // utility class
    }

    public static Culture valueOf(String id) {
        return DAO.valueOf(id);
    }

    public static Culture[] values() {
        return DAO.values();
    }

    private static final class CultureDaoHelper extends EnumerationDao<Culture> {
        private CultureDaoHelper(Class<?extends Identified> instanceType, String query) {
            super(instanceType, query);
        }

        @Override
        protected Culture create(ResultSet result) throws SQLException {
            return new Culture(result.getString("Id"), result.getInt("LEMax"),
                result.getInt("AUMax"), result.getInt("AEMax"), result.getInt("MR"));
        }
    }
}
