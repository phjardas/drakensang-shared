package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.model.Face;
import de.jardas.drakensang.shared.model.Race;
import de.jardas.drakensang.shared.model.Sex;


public final class FaceDao {
    private static final AppearanceFeatureDao<Face> DAO =
        new AppearanceFeatureDao<Face>("face", "faces", "SelectableFaces") {
            @Override
            protected Face create(String id, boolean dwarvish, Sex... sexes) {
                return Face.create(id, dwarvish, sexes);
            }
        };

    private FaceDao() {
        // utility class
    }

    public static Face valueOf(String id) {
        return DAO.valueOf(id);
    }

    public static Face[] values() {
        return DAO.values();
    }

    public static Face[] values(Sex sex, Race race) {
        return DAO.values(sex, race);
    }
}
