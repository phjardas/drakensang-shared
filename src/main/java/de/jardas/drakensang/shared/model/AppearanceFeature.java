package de.jardas.drakensang.shared.model;

import java.util.Arrays;


public abstract class AppearanceFeature implements Identified {
    private final String id;
    private final boolean dwarvish;
    private final Sex[] sexes;

    protected AppearanceFeature(String id, boolean dwarvish, Sex... sexes) {
        this.id = id;
        this.sexes = sexes;
        this.dwarvish = dwarvish;
    }

    public String getId() {
        return id;
    }

    public Sex[] getSexes() {
        return sexes;
    }

    public boolean isDwarvish() {
        return dwarvish;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + id + ", dwarvish=" + dwarvish + ", sexes=" +
        Arrays.toString(sexes) + "]";
    }
}
