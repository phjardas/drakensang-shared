package de.jardas.drakensang.shared.model;

public final class CharSet extends AppearanceFeature {
    private CharSet(String id, boolean dwarvish, Sex... sexes) {
        super(id, dwarvish, sexes);
    }

    public static CharSet create(String id, boolean dwarvish, Sex... sexes) {
        return new CharSet(id, dwarvish, sexes);
    }
}
