package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class IntegerMap {
	private final EventListeners<PropertyChangeListener> listeners = new EventListeners<PropertyChangeListener>();
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
			firePropertyChangeEvent(name, value);
		}
	}

	public abstract String[] getKeys();

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
				ToStringStyle.SHORT_PREFIX_STYLE).toString();
	}
}
