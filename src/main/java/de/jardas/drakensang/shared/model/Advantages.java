package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Advantages implements Iterable<Advantage> {
	private final EventListeners<PropertyChangeListener> listeners = new EventListeners<PropertyChangeListener>();
	private final Set<Advantage> advantages = new HashSet<Advantage>();

	public Set<Advantage> getAdvantages() {
		return advantages;
	}

	public boolean contains(Advantage advantage) {
		return advantages.contains(advantage);
	}

	public Iterator<Advantage> iterator() {
		return advantages.iterator();
	}

	public void add(Advantage advantage) {
		if (advantages.add(advantage)) {
			firePropertyChangeEvent(advantage.getId(), true);
		}
	}

	public void remove(Advantage advantage) {
		if (advantages.remove(advantage)) {
			firePropertyChangeEvent(advantage.getId(), false);
		}
	}

	public int size() {
		return advantages.size();
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
