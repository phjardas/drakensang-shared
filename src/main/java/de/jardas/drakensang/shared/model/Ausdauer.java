package de.jardas.drakensang.shared.model;

public class Ausdauer extends Regenerating {
	public Ausdauer(Person person) {
		super("AU", person, false, "attributes.MU", "attributes.KO",
				"attributes.GE", "race", "culture", "profession");
	}

	@Override
	protected void updateDerivedValues(Person person) {
		final int basis = (int) Math.round((double) (person.getAttribute().get(
				"MU")
				+ person.getAttribute().get("KO") + person.getAttribute().get(
				"GE")) / 2);

		setMaxValue(basis + getBonus()
				+ person.getRace().getAusdauerModifikator()
				+ person.getCulture().getAusdauerModifikator()
				+ person.getProfession().getAusdauerModifikator());
	}
}
