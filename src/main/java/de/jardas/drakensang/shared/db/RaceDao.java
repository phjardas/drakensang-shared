package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.model.Race;

import java.sql.ResultSet;
import java.sql.SQLException;


public final class RaceDao {
    private static final EnumerationDao<Race> DAO =
        new EnumerationDao<Race>(Race.class,
            "select Id, LEMax, AUMax, AEMax, MR from _Template_race order by Id") {
            @Override
            protected Race create(ResultSet result) throws SQLException {
                return new Race(result.getString("Id"), result.getInt("LEMax"),
                    result.getInt("AUMax"), result.getInt("AEMax"), result.getInt("MR"));
            }
        };

    private RaceDao() {
        // utility class
    }

    public static Race valueOf(String id) {
        return DAO.valueOf(id);
    }

    public static Race[] values() {
        return DAO.values();
    }
}
