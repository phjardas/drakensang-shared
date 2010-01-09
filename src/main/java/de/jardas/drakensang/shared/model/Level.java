package de.jardas.drakensang.shared.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import de.jardas.drakensang.shared.db.Messages;


public class Level implements Identified {
    private String id;
    private String name;
    private String worldMapLocation;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorldMapLocation() {
		return this.worldMapLocation;
	}

	public void setWorldMapLocation(String worldMapLocation) {
		this.worldMapLocation = worldMapLocation;
	}
	
	public String getLocalizedName() {
		return Messages.get(getName());
	}

	@Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
