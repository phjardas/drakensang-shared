package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class IntegerMap implements PropertyChangeProducer {
	private final PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);
	private final Map<String, Integer> values = new HashMap<String, Integer>();

	public IntegerMap() {
		for (String key : getKeys()) {
			values.put(key, 0);
		}
	}

	public boolean contains(String key) {
		return values.containsKey(key);
	}

	public int get(String name) {
		return values.get(name);
	}

	public void set(String name, int value) {
		final Integer old = get(name);

		if (old == null || old.intValue() != value) {
			values.put(name, value);
			listeners.firePropertyChange(name, old.intValue(), value);
		}
	}

	public abstract String[] getKeys();

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
}
