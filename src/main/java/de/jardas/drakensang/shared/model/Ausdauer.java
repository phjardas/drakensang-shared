package de.jardas.drakensang.shared.model;

public class Ausdauer extends Regenerating {

	public Ausdauer(Character character) {
		super(character, "attributes.MU", "attributes.KO", "attributes.GE",
				"race", "culture", "profession");
	}

	@Override
	protected void updateDerivedValues(Character character) {
		final int basis = (int) Math.round((double) (character.getAttribute()
				.get("MU")
				+ character.getAttribute().get("KO") + character.getAttribute()
				.get("GE")) / 2);

		setMaxValue(basis + getBonus()
				+ character.getRace().getAusdauerModifikator()
				+ character.getCulture().getAusdauerModifikator()
				+ character.getProfession().getAusdauerModifikator());
	}
}
