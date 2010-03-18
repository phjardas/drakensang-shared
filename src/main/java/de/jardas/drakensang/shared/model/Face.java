package de.jardas.drakensang.shared.model;

public final class Face extends AppearanceFeature {
    private Face(String id, boolean dwarvish, Sex... sexes) {
        super(id, dwarvish, sexes);
    }

    public static Face create(String id, boolean dwarvish, Sex... sexes) {
        return new Face(id, dwarvish, sexes);
    }
}
