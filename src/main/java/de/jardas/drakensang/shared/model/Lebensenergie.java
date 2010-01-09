package de.jardas.drakensang.shared.model;

public class Lebensenergie extends Regenerating {

	public Lebensenergie(Character character) {
		super(character, "attributes.KO", "attributes.KK", "race", "culture",
				"profession");
	}

	@Override
	protected void updateDerivedValues(Character character) {
		int basis = (int) Math.round((double) (character.getAttribute().get(
				"KO") * 2 + character.getAttribute().get("KK")) / 2);

		setMaxValue(basis + getBonus()
				+ character.getRace().getLebensenergieModifikator()
				+ character.getCulture().getLebensenergieModifikator()
				+ character.getProfession().getLebensenergieModifikator());
	}
}
