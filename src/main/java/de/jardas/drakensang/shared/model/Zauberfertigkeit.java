package de.jardas.drakensang.shared.model;

public class Zauberfertigkeit implements Identified {
    private final String id;
    private final String nameKey;
    private final String descriptionKey;
    private final String groupKey;
    private final String attribute;
    private final String[] attributes;

    public Zauberfertigkeit(String id, String nameKey, String descriptionKey, String groupKey,
        String attribute, String[] attributes) {
        super();
        this.id = id;
        this.nameKey = nameKey;
        this.descriptionKey = descriptionKey;
        this.groupKey = groupKey;
        this.attribute = attribute;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public String getNameKey() {
        return nameKey;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public String getAttribute() {
        return attribute;
    }

    public String[] getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getId() + ", " + getAttribute() + "]";
    }
}
