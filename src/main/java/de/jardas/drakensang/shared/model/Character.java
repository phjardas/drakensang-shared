package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import de.jardas.drakensang.shared.model.inventory.Inventory;
import de.jardas.drakensang.shared.model.inventory.Money;

public class Character extends Persistable {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(Character.class);
	private final EventListeners<PropertyChangeListener> listeners = new EventListeners<PropertyChangeListener>();
	private final Talente talente = new Talente();
	private final Sonderfertigkeiten sonderfertigkeiten = new Sonderfertigkeiten();
	private final Zauberfertigkeiten zauberfertigkeiten = new Zauberfertigkeiten();
	private final Attribute attribute = new Attribute();
	private final Advantages advantages = new Advantages();
	private final Inventory inventory = new Inventory();
	private int abenteuerpunkte;
	private int steigerungspunkte;
	private Race race;
	private Culture culture;
	private Profession profession;
	private Sex sex;
	private boolean magician;
	private String lookAtText;
	private boolean localizeLookAtText;
	private CasterType casterType;
	private CasterRace casterRace;
	private double sneakSpeed;
	private double walkSpeed;
	private double runSpeed;
	private double currentSpeed;
	private double maxVelocity;
	private int level;
	private boolean partyMember;
	private boolean currentPartyMember;
	private Hair hair;
	private Face face;
	private CharSet charSet;

	private final DerivedInteger attackeBasis = new DerivedInteger(this) {
		@Override
		protected void updateDerivedValue(Character character) {
			setValue((int) Math.round((double) (getAttribute().get("MU")
					+ getAttribute().get("GE") + getAttribute().get("KK")) / 5));
		}
	};

	private final DerivedInteger paradeBasis = new DerivedInteger(this) {
		@Override
		protected void updateDerivedValue(Character character) {
			setValue((int) Math.round((double) (getAttribute().get("IN")
					+ getAttribute().get("GE") + getAttribute().get("KK")) / 5));
		};
	};

	private final DerivedInteger fernkampfBasis = new DerivedInteger(this) {
		@Override
		protected void updateDerivedValue(Character character) {
			setValue((int) Math.round((double) (getAttribute().get("IN")
					+ getAttribute().get("FF") + getAttribute().get("KK")) / 5));
		};
	};

	private final DerivedInteger magieresistenz = new DerivedInteger(this) {
		@Override
		protected void updateDerivedValue(Character character) {
			setValue((int) Math.round((double) (getAttribute().get("MU")
					+ getAttribute().get("KL") + getAttribute().get("KO")) / 5)
					+ getRace().getMagieresistenzModifikator()
					+ getCulture().getMagieresistenzModifikator()
					+ getProfession().getMagieresistenzModifikator());
		};
	};

	private final Lebensenergie lebensenergie = new Lebensenergie(this);
	private final Astralenergie astralenergie = new Astralenergie(this);
	private final Ausdauer ausdauer = new Ausdauer(this);
	private final Karma karma = new Karma(this);
	private boolean initialized;

	public Character() {
		attribute.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChangeEvent("attributes." + evt.getPropertyName(),
						evt.getNewValue());
			}
		});

		sonderfertigkeiten
				.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						firePropertyChangeEvent("sonderfertigkeiten."
								+ evt.getPropertyName(), evt.getNewValue());
					}
				});

		zauberfertigkeiten
				.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						firePropertyChangeEvent("zauberfertigkeiten."
								+ evt.getPropertyName(), evt.getNewValue());
					}
				});

		talente.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChangeEvent("talente." + evt.getPropertyName(), evt
						.getNewValue());
			}
		});

		advantages.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChangeEvent("advantages." + evt.getPropertyName(),
						evt.getNewValue());
			}
		});

		lebensenergie.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChangeEvent("lebensenergie."
						+ evt.getPropertyName(), evt.getNewValue());
			}
		});

		astralenergie.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChangeEvent("astralenergie."
						+ evt.getPropertyName(), evt.getNewValue());
			}
		});

		ausdauer.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChangeEvent("ausdauer." + evt.getPropertyName(),
						evt.getNewValue());
			}
		});

		karma.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChangeEvent("karma." + evt.getPropertyName(), evt
						.getNewValue());
			}
		});

		attackeBasis.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChangeEvent("attackeBasis", evt.getNewValue());
			}
		});

		paradeBasis.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChangeEvent("paradeBasis", evt.getNewValue());
			}
		});

		fernkampfBasis.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChangeEvent("fernkampfBasis", evt.getNewValue());
			}
		});

		magieresistenz.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				firePropertyChangeEvent("magieresistenz", evt.getNewValue());
			}
		});
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

	public Culture getCulture() {
		return this.culture;
	}

	public void setCulture(Culture culture) {
		if (culture != this.culture) {
			this.culture = culture;
			firePropertyChangeEvent("culture", culture);
		}
	}

	public Profession getProfession() {
		return this.profession;
	}

	public void setProfession(Profession profession) {
		if (profession != this.profession) {
			this.profession = profession;
			firePropertyChangeEvent("profession", profession);
		}
	}

	public Race getRace() {
		return this.race;
	}

	public void setRace(Race race) {
		if (race != this.race) {
			this.race = race;
			firePropertyChangeEvent("race", race);
		}
	}

	public Sex getSex() {
		return this.sex;
	}

	public void setSex(Sex sex) {
		if (sex != this.sex) {
			this.sex = sex;
			firePropertyChangeEvent("sex", sex);
		}
	}

	public String getLookAtText() {
		return lookAtText;
	}

	public void setLookAtText(String lookAtText) {
		if (!new EqualsBuilder().append(this.lookAtText, lookAtText).isEquals()) {
			this.lookAtText = lookAtText;
			firePropertyChangeEvent("lookAtText", lookAtText);
		}
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

	public boolean isMagician() {
		return magician;
	}

	public void setMagician(boolean magician) {
		this.magician = magician;
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
			if (LOG.isDebugEnabled()) {
				LOG.debug("Initializing " + this);
			}

			this.initialized = true;
			firePropertyChangeEvent(null, null);
		}
	}

	protected void firePropertyChangeEvent(String property, Object newValue) {
		if (!this.initialized) {
			return;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("Property changed: " + property + " --> " + newValue);
		}

		final PropertyChangeEvent event = new PropertyChangeEvent(this,
				property, null, newValue);

		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(event);
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener,
			String... properties) {
		if (properties != null && properties.length > 0) {
			listeners.add(new ConfinedPropertyChangeListener(listener,
					properties));
		} else {
			listeners.add(listener);
		}
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.remove(listener);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE).toString();
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
}
