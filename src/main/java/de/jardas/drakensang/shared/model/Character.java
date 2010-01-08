package de.jardas.drakensang.shared.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import de.jardas.drakensang.shared.model.Advantage;
import de.jardas.drakensang.shared.model.Attribute;
import de.jardas.drakensang.shared.model.CasterRace;
import de.jardas.drakensang.shared.model.CasterType;
import de.jardas.drakensang.shared.model.Culture;
import de.jardas.drakensang.shared.model.Persistable;
import de.jardas.drakensang.shared.model.Profession;
import de.jardas.drakensang.shared.model.Race;
import de.jardas.drakensang.shared.model.Sex;
import de.jardas.drakensang.shared.model.Sonderfertigkeiten;
import de.jardas.drakensang.shared.model.Talente;
import de.jardas.drakensang.shared.model.Zauberfertigkeiten;
import de.jardas.drakensang.shared.model.inventory.Inventory;
import de.jardas.drakensang.shared.model.inventory.Money;

public class Character extends Persistable {
	private int abenteuerpunkte;
	private int steigerungspunkte;
	private final Attribute attribute = new Attribute();
	private final Talente talente = new Talente();
	private final Sonderfertigkeiten sonderfertigkeiten = new Sonderfertigkeiten();
	private final Zauberfertigkeiten zauberfertigkeiten = new Zauberfertigkeiten();
	private final Inventory inventory = new Inventory();
	private Race race;
	private Culture culture;
	private Profession profession;
	private Sex sex;
	private boolean magician;
	private String lookAtText;
	private boolean localizeLookAtText;
	private CasterType casterType;
	private CasterRace casterRace;
	private int lebensenergie;
	private int lebensenergieBonus;
	private int astralenergie;
	private int astralenergieBonus;
	private double sneakSpeed;
	private double walkSpeed;
	private double runSpeed;
	private double currentSpeed;
	private double maxVelocity;
	private final Set<Advantage> advantages = new HashSet<Advantage>();
	private int level;
	private boolean partyMember;
	private boolean currentPartyMember;
	private String hair;
	private String face;
	private String body;

	public String getHair() {
		return hair;
	}

	public void setHair(String hair) {
		this.hair = hair;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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

	public Inventory getInventory() {
		return inventory;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public Talente getTalente() {
		return talente;
	}

	public Sonderfertigkeiten getSonderfertigkeiten() {
		return sonderfertigkeiten;
	}

	public Zauberfertigkeiten getZauberfertigkeiten() {
		return zauberfertigkeiten;
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

	public Culture getCulture() {
		return this.culture;
	}

	public void setCulture(Culture culture) {
		this.culture = culture;
	}

	public Profession getProfession() {
		return this.profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	public Race getRace() {
		return this.race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Sex getSex() {
		return this.sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public boolean isMagician() {
		return this.magician;
	}

	public void setMagician(boolean magician) {
		this.magician = magician;
	}

	public String getLookAtText() {
		return lookAtText;
	}

	public void setLookAtText(String lookAtText) {
		this.lookAtText = lookAtText;
	}

	public boolean isLocalizeLookAtText() {
		return localizeLookAtText;
	}

	public void setLocalizeLookAtText(boolean localizeLookAtText) {
		this.localizeLookAtText = localizeLookAtText;
	}

	public CasterType getCasterType() {
		return casterType;
	}

	public void setCasterType(CasterType casterType) {
		this.casterType = casterType;
	}

	public CasterRace getCasterRace() {
		return casterRace;
	}

	public void setCasterRace(CasterRace casterRace) {
		this.casterRace = casterRace;
	}

	public int getAstralenergieBonus() {
		return this.astralenergieBonus;
	}

	public void setAstralenergieBonus(int astralenergieBonus) {
		this.astralenergieBonus = astralenergieBonus;
	}

	public int getLebensenergieBonus() {
		return this.lebensenergieBonus;
	}

	public void setLebensenergieBonus(int lebensenergieBonus) {
		this.lebensenergieBonus = lebensenergieBonus;
	}

	public int getAttackeBasis() {
		return (int) Math.round((double) (getAttribute().get("MU")
				+ getAttribute().get("GE") + getAttribute().get("KK")) / 5);
	}

	public int getParadeBasis() {
		return (int) Math.round((double) (getAttribute().get("IN")
				+ getAttribute().get("GE") + getAttribute().get("KK")) / 5);
	}

	public int getFernkampfBasis() {
		return (int) Math.round((double) (getAttribute().get("IN")
				+ getAttribute().get("FF") + getAttribute().get("KK")) / 5);
	}

	public int getLebensenergie() {
		return lebensenergie;
	}

	public void setLebensenergie(int lebensenergie) {
		this.lebensenergie = lebensenergie;
	}

	public int getAstralenergie() {
		return astralenergie;
	}

	public void setAstralenergie(int astralenergie) {
		this.astralenergie = astralenergie;
	}

	public int getLebensenergieMax() {
		int basis = (int) Math.round((double) (getAttribute().get("KO")
				+ getAttribute().get("KO") + getAttribute().get("KK")) / 2);

		return basis + getLebensenergieBonus()
				+ getRace().getLebensenergieModifikator()
				+ getCulture().getLebensenergieModifikator()
				+ getProfession().getLebensenergieModifikator();
	}

	public int getAusdauer() {
		int basis = (int) Math.round((double) (getAttribute().get("MU")
				+ getAttribute().get("KO") + getAttribute().get("GE")) / 2);

		return basis + getRace().getAusdauerModifikator()
				+ getCulture().getAusdauerModifikator()
				+ getProfession().getAusdauerModifikator();
	}

	public int getAstralenergieMax() {
		int basis = (int) Math.round((double) (getAttribute().get("MU")
				+ getAttribute().get("IN") + getAttribute().get("CH")) / 2);

		return basis + getAstralenergieBonus()
				+ getRace().getAstralenergieModifikator()
				+ getCulture().getAstralenergieModifikator()
				+ getProfession().getAstralenergieModifikator();
	}

	public int getMagieresistenz() {
		return (int) Math.round((double) (getAttribute().get("MU")
				+ getAttribute().get("KL") + getAttribute().get("KO")) / 5)
				+ getRace().getMagieresistenzModifikator()
				+ getCulture().getMagieresistenzModifikator()
				+ getProfession().getMagieresistenzModifikator();
	}

	public Set<Advantage> getAdvantages() {
		return this.advantages;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getSneakSpeed() {
		return sneakSpeed;
	}

	public void setSneakSpeed(double sneakSpeed) {
		this.sneakSpeed = sneakSpeed;
	}

	public double getWalkSpeed() {
		return walkSpeed;
	}

	public void setWalkSpeed(double walkSpeed) {
		this.walkSpeed = walkSpeed;
	}

	public double getRunSpeed() {
		return runSpeed;
	}

	public void setRunSpeed(double runSpeed) {
		this.runSpeed = runSpeed;
	}

	public double getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(double currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public double getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(double maxVelocity) {
		this.maxVelocity = maxVelocity;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE).toString();
	}
}
