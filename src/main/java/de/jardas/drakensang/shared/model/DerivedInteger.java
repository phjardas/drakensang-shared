package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class DerivedInteger implements PropertyChangeProducer {
	private final PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);
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
		listeners.firePropertyChange("value", this.value, this.value = value);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
}
