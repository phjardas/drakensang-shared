package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class DerivedInteger {
	private final EventListeners<PropertyChangeListener> listeners = new EventListeners<PropertyChangeListener>();
	private final Person person;
	private int value;

	protected DerivedInteger(Person person, String... properties) {
		this.person = person;
		person.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				updateDerivedValue(DerivedInteger.this.person);
			}
		}, properties);
	}

	protected abstract void updateDerivedValue(Person person);

	public int getValue() {
		return value;
	}

	protected void setValue(int value) {
		if (value != this.value) {
			this.value = value;
			firePropertyChangeEvent("value", value);
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
		return String.valueOf(getValue());
	}
}