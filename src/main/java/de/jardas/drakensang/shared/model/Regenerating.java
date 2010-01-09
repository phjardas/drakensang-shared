package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class Regenerating {
	private final PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);
	private final Person person;
	private final String name;
	private int currentValue;
	private int maxValue;
	private int regenerationAmount;
	private int regenerationFrequency;
	private int regenerationFrequencyCombat;
	private final boolean hasBonus;
	private int bonus;

	protected Regenerating(String name, Person person, boolean hasBonus,
			String... properties) {
		this.name = name;
		this.person = person;
		this.hasBonus = hasBonus;

		final PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				updateDerivedValues(Regenerating.this.person);
			}
		};

		person.addPropertyChangeListener(propertyChangeListener, properties);
		addPropertyChangeListener(propertyChangeListener);
	}

	protected abstract void updateDerivedValues(Person person);

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		if (currentValue > getMaxValue()) {
			setCurrentValue(maxValue);
		} else {
			listeners.firePropertyChange("currentValue", this.currentValue,
					this.currentValue = currentValue);
		}
	}

	public int getMaxValue() {
		return maxValue;
	}

	protected void setMaxValue(int maxValue) {
		listeners.firePropertyChange("maxValue", this.maxValue,
				this.maxValue = maxValue);

		if (getCurrentValue() > maxValue) {
			setCurrentValue(maxValue);
		}
	}

	public int getRegenerationAmount() {
		return regenerationAmount;
	}

	public void setRegenerationAmount(int regenerationAmount) {
		listeners.firePropertyChange("regenerationAmount",
				this.regenerationAmount,
				this.regenerationAmount = regenerationAmount);
	}

	public int getRegenerationFrequency() {
		return regenerationFrequency;
	}

	public void setRegenerationFrequency(int regenerationFrequency) {
		listeners.firePropertyChange("regenerationFrequency",
				this.regenerationFrequency,
				this.regenerationFrequency = regenerationFrequency);
	}

	public int getRegenerationFrequencyCombat() {
		return regenerationFrequencyCombat;
	}

	public void setRegenerationFrequencyCombat(int regenerationFrequencyCombat) {
		listeners.firePropertyChange("regenerationFrequencyCombat",
				this.regenerationFrequencyCombat,
				this.regenerationFrequencyCombat = regenerationFrequencyCombat);
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		if (!hasBonus) {
			throw new IllegalArgumentException(getName() + " has no bonus.");
		}

		listeners.firePropertyChange("bonus", this.bonus, this.bonus = bonus);
	}

	public boolean hasBonus() {
		return hasBonus;
	}

	public String getName() {
		return name;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
