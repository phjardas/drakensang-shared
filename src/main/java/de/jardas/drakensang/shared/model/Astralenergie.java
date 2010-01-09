package de.jardas.drakensang.shared.model;

public class Astralenergie extends Regenerating {

	public Astralenergie(Character character) {
		super(character, "attributes.MU", "attributes.IN", "attributes.CH",
				"race", "culture", "profession");
	}

	@Override
	protected void updateDerivedValues(Character character) {
		final int basis = (int) Math.round((double) (character.getAttribute()
				.get("MU")
				+ character.getAttribute().get("IN") + character.getAttribute()
				.get("CH")) / 2);

		setMaxValue(basis + getBonus()
				+ character.getRace().getAstralenergieModifikator()
				+ character.getCulture().getAstralenergieModifikator()
				+ character.getProfession().getAstralenergieModifikator());
	}
}
