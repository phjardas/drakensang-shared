package de.jardas.drakensang.shared.model;

import de.jardas.drakensang.shared.db.Static;

public enum Race implements Identified {
	Elf, Halbelf, Mittellaender, Thorwaler, Tulamide, Zwerg;

	private final int lebensenergieModifikator;
	private final int ausdauerModifikator;
	private final int astralenergieModifikator;
	private final int magieresistenzModifikator;
	private final int karmaModifikator = 0;

	private Race() {
		lebensenergieModifikator = Integer.parseInt(Static.get("LEMax", name(),
				"id", "_template_race"));
		ausdauerModifikator = Integer.parseInt(Static.get("AUMax", name(),
				"id", "_template_race"));
		astralenergieModifikator = Integer.parseInt(Static.get("AEMax", name(),
				"id", "_template_race"));
		magieresistenzModifikator = Integer.parseInt(Static.get("MR", name(),
				"id", "_template_race"));
	}
	
	public String getId() {
		return name();
	}

	public int getKarmaModifikator() {
		return karmaModifikator;
	}

	public int getLebensenergieModifikator() {
		return lebensenergieModifikator;
	}

	public int getAusdauerModifikator() {
		return ausdauerModifikator;
	}

	public int getAstralenergieModifikator() {
		return astralenergieModifikator;
	}

	public int getMagieresistenzModifikator() {
		return magieresistenzModifikator;
	}
}
