package de.jardas.drakensang.shared.model;

public class Karma extends Regenerating {

	public Karma(Person person) {
		super("KE", person, false, "race", "culture", "profession");
	}

	@Override
	protected void updateDerivedValues(Person person) {
		// FIXME Berechnung des Karma?
		final int basis = 0;

		setMaxValue(basis + getBonus() + person.getRace().getKarmaModifikator()
				+ person.getCulture().getKarmaModifikator()
				+ person.getProfession().getKarmaModifikator());
	}
}
