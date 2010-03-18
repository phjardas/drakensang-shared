package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.model.CharSet;
import de.jardas.drakensang.shared.model.Race;
import de.jardas.drakensang.shared.model.Sex;


public final class CharSetDao {
    private static final AppearanceFeatureDao<CharSet> DAO =
        new AppearanceFeatureDao<CharSet>("charset", "charsets", "SelectableCharSets") {
            @Override
            protected CharSet create(String id, boolean dwarvish, Sex... sexes) {
                return CharSet.create(id, dwarvish, sexes);
            }
        };

    private CharSetDao() {
        // utility class
    }

    public static CharSet valueOf(String id) {
        return DAO.valueOf(id);
    }

    public static CharSet[] values() {
        return DAO.values();
    }

    public static CharSet[] values(Sex sex, Race race) {
        return DAO.values(sex, race);
    }
}
