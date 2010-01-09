package de.jardas.drakensang.shared.model;

import java.beans.PropertyChangeListener;

public interface PropertyChangeProducer {
	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);
}
