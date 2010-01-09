package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class Regenerating {
	private final EventListeners<PropertyChangeListener> listeners = new EventListeners<PropertyChangeListener>();
	private final Character character;
	private int currentValue;
	private int maxValue;
	private int regenerationAmount;
	private int regenerationFrequency;
	private int regenerationFrequencyCombat;
	private int bonus;

	protected Regenerating(Character character, String... properties) {
		this.character = character;

		character.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				updateDerivedValues(Regenerating.this.character);
			}
		}, properties);
	}

	protected abstract void updateDerivedValues(Character character);

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		if (currentValue > getMaxValue()) {
			setCurrentValue(maxValue);
		} else if (currentValue != this.currentValue) {
			this.currentValue = currentValue;
			firePropertyChangeEvent("currentValue", currentValue);
		}
	}

	public int getMaxValue() {
		return maxValue;
	}

	protected void setMaxValue(int maxValue) {
		if (maxValue != this.maxValue) {
			this.maxValue = maxValue;
			firePropertyChangeEvent("maxValue", maxValue);

			if (getCurrentValue() > maxValue) {
				setCurrentValue(maxValue);
			}
		}
	}

	public int getRegenerationAmount() {
		return regenerationAmount;
	}

	public void setRegenerationAmount(int regenerationAmount) {
		if (regenerationAmount != this.regenerationAmount) {
			this.regenerationAmount = regenerationAmount;
			firePropertyChangeEvent("regenerationAmount", regenerationAmount);
		}
	}

	public int getRegenerationFrequency() {
		return regenerationFrequency;
	}

	public void setRegenerationFrequency(int regenerationFrequency) {
		if (regenerationFrequency != this.regenerationFrequency) {
			this.regenerationFrequency = regenerationFrequency;
			firePropertyChangeEvent("regenerationFrequency",
					regenerationFrequency);
		}
	}

	public int getRegenerationFrequencyCombat() {
		return regenerationFrequencyCombat;
	}

	public void setRegenerationFrequencyCombat(int regenerationFrequencyCombat) {
		if (regenerationFrequencyCombat != this.regenerationFrequencyCombat) {
			this.regenerationFrequencyCombat = regenerationFrequencyCombat;
			firePropertyChangeEvent("regenerationFrequencyCombat",
					regenerationFrequencyCombat);
		}
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		if (bonus != this.bonus) {
			this.bonus = bonus;
			firePropertyChangeEvent("bonus", bonus);
			updateDerivedValues(character);
		}
	}

	protected void firePropertyChangeEvent(String property, int newValue) {
		final PropertyChangeEvent event = new PropertyChangeEvent(this,
				property, null, newValue);

		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(event);
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.add(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.remove(listener);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
