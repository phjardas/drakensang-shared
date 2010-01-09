package de.jardas.drakensang.shared.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventListeners<E> implements Iterable<E> {
	private final List<E> listeners = new ArrayList<E>();

	public void add(E listener) {
		listeners.add(listener);
	}

	public void remove(E listener) {
		listeners.remove(listener);
	}

	public Iterator<E> iterator() {
		return listeners.iterator();
	}
}
