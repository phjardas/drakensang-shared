package de.jardas.drakensang.shared.model;

public enum CasterType implements Identified {
	none,
	halfmage,
	fullmage,
	priest,
	geode,
	;
	
	public String getId() {
		return name();
	}
}
