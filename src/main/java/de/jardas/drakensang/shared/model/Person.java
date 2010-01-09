package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import de.jardas.drakensang.shared.model.inventory.Inventory;
import de.jardas.drakensang.shared.model.inventory.Money;

public abstract class Person extends Persistable {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(Person.class);
	private final EventListeners<PropertyChangeListener> listeners = new EventListeners<PropertyChangeListener>();
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

	public boolean isMagician() {
		return magician;
	}

	public void setMagician(boolean magician) {
		this.magician = magician;
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
			firePropertyChangeEvent(null, null);
		}
	}

	protected void firePropertyChangeEvent(String property, Object newValue) {
		if (!this.initialized) {
			return;
		}

		LOG.debug("Property changed: {} --> {}", property, newValue);

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
}
