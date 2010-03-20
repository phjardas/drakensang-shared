package de.jardas.drakensang.shared.model;

public final class Profession implements Identified {
	// steht in _instance_pc
	private final String profession;
	// steht in _template_profession
	private final String spezialization;
	private final int lebensenergieModifikator;
	private final int ausdauerModifikator;
	private final int astralenergieModifikator;
	private final int magieresistenzModifikator;

	public Profession(String profession, String spezialization,
			int lebensenergieModifikator, int ausdauerModifikator,
			int astralenergieModifikator, int magieresistenzModifikator) {
		super();
		this.profession = profession;
		this.spezialization = spezialization;
		this.lebensenergieModifikator = lebensenergieModifikator;
		this.ausdauerModifikator = ausdauerModifikator;
		this.astralenergieModifikator = astralenergieModifikator;
		this.magieresistenzModifikator = magieresistenzModifikator;
	}

	public String getId() {
		return profession;
	}

	public String getProfession() {
		return profession;
	}

	public String getSpezialization() {
		return spezialization;
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

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + getProfession() + ", "
				+ getSpezialization() + "]";
	}
}
