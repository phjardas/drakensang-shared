package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.model.Hair;
import de.jardas.drakensang.shared.model.Race;
import de.jardas.drakensang.shared.model.Sex;


public final class HairDao {
    private static final AppearanceFeatureDao<Hair> DAO =
        new AppearanceFeatureDao<Hair>("hair", "hairs", "SelectableHairs") {
            @Override
            protected Hair create(String id, boolean dwarvish, Sex... sexes) {
                return Hair.create(id, dwarvish, sexes);
            }
        };

    private HairDao() {
        // utility class
    }

    public static Hair valueOf(String id) {
        return DAO.valueOf(id);
    }

    public static Hair[] values() {
        return DAO.values();
    }

    public static Hair[] values(Sex sex, Race race) {
        return DAO.values(sex, race);
    }
}
