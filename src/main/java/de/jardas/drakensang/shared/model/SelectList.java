package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SelectList<I extends Identified> implements Iterable<I>,
		PropertyChangeProducer {
	private final PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);
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
			listeners.firePropertyChange(item.getId(), false, true);
		}
	}

	public void remove(I item) {
		if (items.remove(item)) {
			listeners.firePropertyChange(item.getId(), true, false);
		}
	}

	public int size() {
		return items.size();
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}
}
