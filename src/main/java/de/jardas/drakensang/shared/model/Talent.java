package de.jardas.drakensang.shared.model;

public class Talent implements Identified {
    private final String id;
    private final String nameKey;
    private final String descriptionKey;
    private final String attribute;
    private final String categoryKey;
    private final String groupKey;
    private final String[] attributes;

    public Talent(String id, String nameKey, String descriptionKey, String attribute,
        String categoryKey, String groupKey, String[] attributes) {
        super();
        this.id = id;
        this.nameKey = nameKey;
        this.descriptionKey = descriptionKey;
        this.attribute = attribute;
        this.categoryKey = categoryKey;
        this.groupKey = groupKey;
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

    public String getCategoryKey() {
        return categoryKey;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public String getAttribute() {
        return attribute;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getId() + ", " + getAttribute() + "]";
    }
}
