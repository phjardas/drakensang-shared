package de.jardas.drakensang.shared.model;

import de.jardas.drakensang.shared.db.Static;

public enum Race {
	Elf,
	Halbelf,
	Mittellaender,
	Thorwaler,
	Tulamide,
	Zwerg;
	
	public int getLebensenergieModifikator() {
		return Integer.parseInt(Static.get("LEMax", name(), "id", "_template_race"));
	}
	
	public int getAusdauerModifikator() {
		return Integer.parseInt(Static.get("AUMax", name(), "id", "_template_race"));
	}
	
	public int getAstralenergieModifikator() {
		return Integer.parseInt(Static.get("AEMax", name(), "id", "_template_race"));
	}
	
	public int getMagieresistenzModifikator() {
		return Integer.parseInt(Static.get("MR", name(), "id", "_template_race"));
	}
}
