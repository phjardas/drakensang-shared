package de.jardas.drakensang.shared.model;

public enum CasterRace implements Identified {
	human,
	dwarf,
	elvish,
	;
	
	public String getId() {
		return name();
	}
}
