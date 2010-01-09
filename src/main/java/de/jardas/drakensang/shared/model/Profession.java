package de.jardas.drakensang.shared.model;

import de.jardas.drakensang.shared.db.Static;

public enum Profession implements Identified {
	Alchimist, Amazone, Bogenschuetze, Dieb, Einbrecher, Elementarist, Geode, Heilmagier, Kaempfer, Kampfmagier, Krieger, Metamagier, Pirat, Prospektor, Sappeur, Scharlatan, Soeldner, Soldat, Streuner, Stammeskrieger, Waldlaeufer, Zauberweber;

	private final String profession;
	private final String specialization;
	private final int lebensenergieModifikator;
	private final int ausdauerModifikator;
	private final int astralenergieModifikator;
	private final int magieresistenzModifikator;
	private final int karmaModifikator;

	private Profession() {
		specialization = name();
		profession = "Stammeskrieger".equals(specialization) ? "Barbar"
				: specialization;

		lebensenergieModifikator = Integer.parseInt(Static.get("LEMax",
				specialization, "id", "_template_profession"));
		ausdauerModifikator = Integer.parseInt(Static.get("AUMax",
				specialization, "id", "_template_profession"));
		astralenergieModifikator = Integer.parseInt(Static.get("AEMax",
				specialization, "id", "_template_profession"));
		magieresistenzModifikator = Integer.parseInt(Static.get("MR",
				specialization, "id", "_template_profession"));
		karmaModifikator = 0;
	}

	public static Profession forProfession(String p) {
		for (Profession prof : values()) {
			if (prof.getProfession().equals(p)) {
				return prof;
			}
		}

		throw new IllegalArgumentException(
				"No profession known for profession '" + p + "'");
	}

	public static Profession forSpecialization(String spec) {
		for (Profession prof : values()) {
			if (prof.getSpecialization().equals(spec)) {
				return prof;
			}
		}

		throw new IllegalArgumentException(
				"No profession known for specialization '" + spec + "'");
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

	public String getProfession() {
		return profession;
	}

	public String getSpecialization() {
		return specialization;
	}
}
