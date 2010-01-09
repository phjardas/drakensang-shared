package de.jardas.drakensang.shared.model;

public class Lebensenergie extends Regenerating {

	public Lebensenergie(Person person, boolean hasBonus) {
		super("LE", person, hasBonus, "attributes.KO", "attributes.KK", "race",
				"culture", "profession");
	}

	@Override
	protected void updateDerivedValues(Person person) {
		int basis = (int) Math
				.round((double) (person.getAttribute().get("KO") * 2 + person
						.getAttribute().get("KK")) / 2);

		setMaxValue(basis + getBonus()
				+ person.getRace().getLebensenergieModifikator()
				+ person.getCulture().getLebensenergieModifikator()
				+ person.getProfession().getLebensenergieModifikator());
	}
}
