package de.jardas.drakensang.shared.model;

public final class Hair extends AppearanceFeature {
    private Hair(String id, boolean dwarvish, Sex... sexes) {
        super(id, dwarvish, sexes);
    }

    public static Hair create(String id, boolean dwarvish, Sex... sexes) {
        return new Hair(id, dwarvish, sexes);
    }
}
