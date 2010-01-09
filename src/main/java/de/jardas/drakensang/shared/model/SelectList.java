package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SelectList<I extends Enum<I>> implements Iterable<I> {
	private final EventListeners<PropertyChangeListener> listeners = new EventListeners<PropertyChangeListener>();
	private final Set<I> items = new HashSet<I>();

	public Set<I> getAll() {
		return items;
	}

	public boolean contains(I item) {
		return items.contains(item);
	}

	public Iterator<I> iterator() {
		return items.iterator();
	}

	public void add(I item) {
		if (items.add(item)) {
			firePropertyChangeEvent(item.name(), true);
		}
	}

	public void remove(I item) {
		if (items.remove(item)) {
			firePropertyChangeEvent(item.name(), false);
		}
	}

	public int size() {
		return items.size();
	}

	protected void firePropertyChangeEvent(String property, boolean existing) {
		final PropertyChangeEvent event = new PropertyChangeEvent(this,
				property, null, existing);

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
}
