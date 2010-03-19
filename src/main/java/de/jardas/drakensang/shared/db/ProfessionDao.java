package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.model.Identified;
import de.jardas.drakensang.shared.model.Profession;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProfessionDao {
    private static final Dao DAO =
        new Dao(Profession.class,
            "select Id, LEMax, AUMax, AEMax, MR from _Template_profession order by Id");

    private ProfessionDao() {
        // utility class
    }

    public static Profession valueOf(String id) {
        return DAO.valueOf(id);
    }

    public static Profession[] values() {
        return DAO.values();
    }

    private static final class Dao extends EnumerationDao<Profession> {
        private Dao(Class<?extends Identified> instanceType, String query) {
            super(instanceType, query);
        }

        @Override
        protected Profession create(ResultSet result) throws SQLException {
            return new Profession(result.getString("Id"), result.getInt("LEMax"),
                result.getInt("AUMax"), result.getInt("AEMax"), result.getInt("MR"));
        }
    }
}
