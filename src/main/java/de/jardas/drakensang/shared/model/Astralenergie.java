package de.jardas.drakensang.shared.model;

public class Astralenergie extends Regenerating {

	public Astralenergie(Person person, boolean hasBonus) {
		super("AE", person, hasBonus, "attributes.MU", "attributes.IN",
				"attributes.CH", "race", "culture", "profession");
	}

	@Override
	protected void updateDerivedValues(Person person) {
		final int basis = (int) Math.round((double) (person.getAttribute().get(
				"MU")
				+ person.getAttribute().get("IN") + person.getAttribute().get(
				"CH")) / 2);

		setMaxValue(basis + getBonus()
				+ person.getRace().getAstralenergieModifikator()
				+ person.getCulture().getAstralenergieModifikator()
				+ person.getProfession().getAstralenergieModifikator());
	}
}
