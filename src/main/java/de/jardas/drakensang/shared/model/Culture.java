package de.jardas.drakensang.shared.model;

import de.jardas.drakensang.shared.db.Static;

public enum Culture implements Identified {
	Amazone,
	Ambosszwerg,
	Andergaster,
	Auelf,
	Garetier,
	Horasier,
	Mhanadistani,
	Mittelreicher,
	Gjalskerlaender,
	Novadi,
	Thorwaler,
	Waldelf,
	;

	private final int lebensenergieModifikator;
	private final int ausdauerModifikator;
	private final int astralenergieModifikator;
	private final int magieresistenzModifikator;
	private final int karmaModifikator;

	private Culture() {
		lebensenergieModifikator = Integer.parseInt(Static.get("LEMax", name(),
				"id", "_template_culture"));
		ausdauerModifikator = Integer.parseInt(Static.get("AUMax", name(),
				"id", "_template_culture"));
		astralenergieModifikator = Integer.parseInt(Static.get("AEMax", name(),
				"id", "_template_culture"));
		magieresistenzModifikator = Integer.parseInt(Static.get("MR", name(),
				"id", "_template_culture"));
		karmaModifikator = 0;
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
	
	public String getId() {
		return name();
	}
}
