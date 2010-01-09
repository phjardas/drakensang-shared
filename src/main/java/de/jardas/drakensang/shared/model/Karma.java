package de.jardas.drakensang.shared.model;

public class Karma extends Regenerating {

	public Karma(Character character) {
		super(character, "race", "culture", "profession");
	}

	@Override
	protected void updateDerivedValues(Character character) {
		// FIXME Berechnung des Karma?
		final int basis = 0;

		setMaxValue(basis + getBonus()
				+ character.getRace().getKarmaModifikator()
				+ character.getCulture().getKarmaModifikator()
				+ character.getProfession().getKarmaModifikator());
	}
}
