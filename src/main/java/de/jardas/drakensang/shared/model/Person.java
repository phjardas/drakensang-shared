package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import de.jardas.drakensang.shared.model.inventory.Inventory;
import de.jardas.drakensang.shared.model.inventory.Money;

public abstract class Person extends Persistable implements
		PropertyChangeProducer {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(Person.class);
	private final PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);
	private final Talente talente = new Talente();
	private final Sonderfertigkeiten sonderfertigkeiten = new Sonderfertigkeiten();
	private final Zauberfertigkeiten zauberfertigkeiten = new Zauberfertigkeiten();
	private final Attribute attribute = new Attribute();
	private final Advantages advantages = new Advantages();
	private final Inventory inventory = new Inventory();
	private Race race;
	private Culture culture;
	private Profession profession;
	private Sex sex;
	private boolean magician;
	private String lookAtText;
	private CasterType casterType;
	private CasterRace casterRace;
	private double sneakSpeed;
	private double walkSpeed;
	private double runSpeed;
	private double currentSpeed;

	private final DerivedInteger attackeBasis = new DerivedInteger(this) {
		@Override
		protected void updateDerivedValue(Person character) {
			setValue((int) Math.round((double) (getAttribute().get("MU")
					+ getAttribute().get("GE") + getAttribute().get("KK")) / 5));
		}
	};

	private final DerivedInteger paradeBasis = new DerivedInteger(this) {
		@Override
		protected void updateDerivedValue(Person character) {
			setValue((int) Math.round((double) (getAttribute().get("IN")
					+ getAttribute().get("GE") + getAttribute().get("KK")) / 5));
		};
	};

	private final DerivedInteger fernkampfBasis = new DerivedInteger(this) {
		@Override
		protected void updateDerivedValue(Person character) {
			setValue((int) Math.round((double) (getAttribute().get("IN")
					+ getAttribute().get("FF") + getAttribute().get("KK")) / 5));
		};
	};

	private final DerivedInteger magieresistenz = new DerivedInteger(this) {
		@Override
		protected void updateDerivedValue(Person character) {
			setValue((int) Math.round((double) (getAttribute().get("MU")
					+ getAttribute().get("KL") + getAttribute().get("KO")) / 5)
					+ getRace().getMagieresistenzModifikator()
					+ getCulture().getMagieresistenzModifikator()
					+ getProfession().getMagieresistenzModifikator());
		};
	};

	private final Lebensenergie lebensenergie;
	private final Astralenergie astralenergie;
	private final Ausdauer ausdauer = new Ausdauer(this);
	private final Karma karma = new Karma(this);
	private boolean initialized;

	protected Person(boolean withLEBonus, boolean withAEBonus) {
		lebensenergie = new Lebensenergie(this, withLEBonus);
		astralenergie = new Astralenergie(this, withAEBonus);

		attribute
				.addPropertyChangeListener(new ForwardingPropertyChangeListener(
						"attributes."));
		sonderfertigkeiten
				.addPropertyChangeListener(new ForwardingPropertyChangeListener(
						"sonderfertigkeiten."));
		zauberfertigkeiten
				.addPropertyChangeListener(new ForwardingPropertyChangeListener(
						"zauberfertigkeiten."));
		talente.addPropertyChangeListener(new ForwardingPropertyChangeListener(
				"talente."));
		advantages
				.addPropertyChangeListener(new ForwardingPropertyChangeListener(
						"advantages."));
		lebensenergie
				.addPropertyChangeListener(new ForwardingPropertyChangeListener(
						"lebensenergie."));
		astralenergie
				.addPropertyChangeListener(new ForwardingPropertyChangeListener(
						"astralenergie."));
		ausdauer
				.addPropertyChangeListener(new ForwardingPropertyChangeListener(
						"ausdauer."));
		karma.addPropertyChangeListener(new ForwardingPropertyChangeListener(
				"karma."));
		attackeBasis
				.addPropertyChangeListener(new ForwardingPropertyChangeListener(
						"attackeBasis."));
		paradeBasis
				.addPropertyChangeListener(new ForwardingPropertyChangeListener(
						"paradeBasis."));
		fernkampfBasis
				.addPropertyChangeListener(new ForwardingPropertyChangeListener(
						"fernkampfBasis."));
		magieresistenz
				.addPropertyChangeListener(new ForwardingPropertyChangeListener(
						"magieresistenz."));
	}

	public int getMoneyAmount() {
		final Set<Money> money = getInventory().getItems(Money.class);

		return money.isEmpty() ? 0 : money.iterator().next().getCount();
	}

	public void setMoneyAmount(int amount) {
		final Set<Money> moneys = getInventory().getItems(Money.class);

		if (moneys.size() != 1) {
			throw new IllegalArgumentException("The character " + getName()
					+ " can not carry money.");
		}

		final Money money = moneys.iterator().next();
		final int old = money.getCount();
		money.setCount(amount);
		firePropertyChange("money", old, amount);
	}

	public Culture getCulture() {
		return this.culture;
	}

	public void setCulture(Culture culture) {
		firePropertyChange("culture", culture, this.culture = culture);
	}

	public Profession getProfession() {
		return this.profession;
	}

	public void setProfession(Profession profession) {
		firePropertyChange("profession", profession,
				this.profession = profession);
	}

	public Race getRace() {
		return this.race;
	}

	public void setRace(Race race) {
		firePropertyChange("race", race, this.race = race);
	}

	public Sex getSex() {
		return this.sex;
	}

	public void setSex(Sex sex) {
		firePropertyChange("sex", sex, this.sex = sex);
	}

	public String getLookAtText() {
		return lookAtText;
	}

	public void setLookAtText(String lookAtText) {
		firePropertyChange("lookAtText", this.lookAtText,
				this.lookAtText = lookAtText);
	}

	public boolean isMagician() {
		return magician;
	}

	public void setMagician(boolean magician) {
		if (magician != this.magician) {
			firePropertyChange("magician", this.magician,
					this.magician = magician);
		}
	}

	public CasterType getCasterType() {
		return casterType;
	}

	public void setCasterType(CasterType casterType) {
		firePropertyChange("casterType", this.casterType,
				this.casterType = casterType);
	}

	public CasterRace getCasterRace() {
		return casterRace;
	}

	public void setCasterRace(CasterRace casterRace) {
		firePropertyChange("casterRace", this.casterRace,
				this.casterRace = casterRace);
	}

	public double getSneakSpeed() {
		return sneakSpeed;
	}

	public void setSneakSpeed(double sneakSpeed) {
		firePropertyChange("sneakSpeed", this.sneakSpeed,
				this.sneakSpeed = sneakSpeed);
	}

	public double getWalkSpeed() {
		return walkSpeed;
	}

	public void setWalkSpeed(double walkSpeed) {
		firePropertyChange("walkSpeed", this.walkSpeed,
				this.walkSpeed = walkSpeed);
	}

	public double getRunSpeed() {
		return runSpeed;
	}

	public void setRunSpeed(double runSpeed) {
		firePropertyChange("runSpeed", this.runSpeed, this.runSpeed = runSpeed);
	}

	public double getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(double currentSpeed) {
		firePropertyChange("currentSpeed", this.currentSpeed,
				this.currentSpeed = currentSpeed);
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

	public Attribute getAttribute() {
		return attribute;
	}

	public Advantages getAdvantages() {
		return advantages;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public DerivedInteger getAttackeBasis() {
		return attackeBasis;
	}

	public DerivedInteger getParadeBasis() {
		return paradeBasis;
	}

	public DerivedInteger getFernkampfBasis() {
		return fernkampfBasis;
	}

	public DerivedInteger getMagieresistenz() {
		return magieresistenz;
	}

	public Lebensenergie getLebensenergie() {
		return lebensenergie;
	}

	public Astralenergie getAstralenergie() {
		return astralenergie;
	}

	public Ausdauer getAusdauer() {
		return ausdauer;
	}

	public Karma getKarma() {
		return karma;
	}

	public void initialized() {
		if (!this.initialized) {
			LOG.debug("Initializing {}", this);
			this.initialized = true;
			firePropertyChange(null, null, null);
		}
	}

	protected void firePropertyChange(String property, Object oldValue,
			Object newValue) {
		if (!this.initialized) {
			return;
		}

		if (property != null && LOG.isDebugEnabled()) {
			LOG.debug("Property {}: {} --> {}", new Object[] { property,
					oldValue, newValue });
		}

		listeners.firePropertyChange(property, oldValue, newValue);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		addPropertyChangeListener(listener, (String[]) null);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener,
			String... properties) {
		if (properties != null && properties.length > 0) {
			listeners
					.addPropertyChangeListener(new ConfinedPropertyChangeListener(
							listener, properties));
		} else {
			listeners.addPropertyChangeListener(listener);
		}
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}

	private static class ConfinedPropertyChangeListener implements
			PropertyChangeListener {
		private final PropertyChangeListener listener;
		private final String[] properties;

		public ConfinedPropertyChangeListener(PropertyChangeListener listener,
				String[] properties) {
			this.listener = listener;
			this.properties = properties;
		}

		public void propertyChange(PropertyChangeEvent evt) {
			if (isInterested(evt)) {
				listener.propertyChange(evt);
			}
		}

		private boolean isInterested(PropertyChangeEvent evt) {
			final String propertyName = evt.getPropertyName();

			if (propertyName == null) {
				return true;
			}

			for (String property : properties) {
				if (property.equals(propertyName)) {
					return true;
				}

				if (property.endsWith("*")
						&& propertyName.startsWith(property.substring(0,
								property.indexOf("*")))) {
					return true;
				}
			}

			return false;
		}
	}

	private class ForwardingPropertyChangeListener implements
			PropertyChangeListener {
		private final String prefix;

		private ForwardingPropertyChangeListener(String prefix) {
			super();
			this.prefix = prefix;
		}

		public void propertyChange(PropertyChangeEvent evt) {
			firePropertyChange(prefix + evt.getPropertyName(), evt
					.getOldValue(), evt.getNewValue());
		}
	}
}
