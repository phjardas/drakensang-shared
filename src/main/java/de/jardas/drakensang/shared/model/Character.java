package de.jardas.drakensang.shared.model;

import java.util.Set;

import de.jardas.drakensang.shared.model.inventory.Money;

public class Character extends Person {
	private int abenteuerpunkte;
	private int steigerungspunkte;
	private boolean localizeLookAtText;
	private double maxVelocity;
	private int level;
	private boolean partyMember;
	private boolean currentPartyMember;
	private Hair hair;
	private Face face;
	private CharSet charSet;

	public Character() {
		super(false, false);
	}

	public boolean isPlayerCharacter() {
		return "CharWizardPC".equals(getId());
	}

	public int getMoneyAmount() {
		Set<Money> money = getInventory().getItems(Money.class);

		return money.isEmpty() ? 0 : money.iterator().next().getCount();
	}

	public void setMoneyAmount(int amount) {
		Set<Money> money = getInventory().getItems(Money.class);

		if (money.size() != 1) {
			throw new IllegalArgumentException("The character " + getName()
					+ " can not carry money.");
		}

		money.iterator().next().setCount(amount);
	}

	public int getAbenteuerpunkte() {
		return abenteuerpunkte;
	}

	public void setAbenteuerpunkte(int abenteuerpunkte) {
		this.abenteuerpunkte = abenteuerpunkte;
	}

	public int getSteigerungspunkte() {
		return steigerungspunkte;
	}

	public void setSteigerungspunkte(int steigerungspunkte) {
		this.steigerungspunkte = steigerungspunkte;
	}

	public boolean isLocalizeLookAtText() {
		return localizeLookAtText;
	}

	public void setLocalizeLookAtText(boolean localizeLookAtText) {
		this.localizeLookAtText = localizeLookAtText;
	}

	public double getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(double maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isPartyMember() {
		return partyMember;
	}

	public void setPartyMember(boolean partyMember) {
		this.partyMember = partyMember;
	}

	public boolean isCurrentPartyMember() {
		return currentPartyMember;
	}

	public void setCurrentPartyMember(boolean currentPartyMember) {
		this.currentPartyMember = currentPartyMember;
	}

	public Hair getHair() {
		return hair;
	}

	public void setHair(Hair hair) {
		this.hair = hair;
	}

	public Face getFace() {
		return face;
	}

	public void setFace(Face face) {
		this.face = face;
	}

	public CharSet getCharSet() {
		return charSet;
	}

	public void setCharSet(CharSet charSet) {
		this.charSet = charSet;
	}
}
