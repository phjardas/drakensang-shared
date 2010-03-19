package de.jardas.drakensang.shared.model;

public class Sonderfertigkeit implements Identified {
    private final String id;
    private final String name;
    private final String info;
    private final String attribute;
    private final String categoryKey;

    public Sonderfertigkeit(String id, String name, String info, String attribute,
        String categoryKey) {
        super();
        this.id = id;
        this.name = name;
        this.info = info;
        this.attribute = attribute;
        this.categoryKey = categoryKey;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public String getAttribute() {
        return attribute;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getId() + ", " + getAttribute() + "]";
    }
}
