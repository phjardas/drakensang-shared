package de.jardas.drakensang.shared.model.inventory;

import java.util.HashSet;
import java.util.Set;

public class Inventory {
	private final Set<InventoryItem> items = new HashSet<InventoryItem>();

	public Set<InventoryItem> getItems() {
		return items;
	}

	public void addItem(InventoryItem item) {
		items.add(item);
	}

	@SuppressWarnings("unchecked")
	public <T extends InventoryItem> Set<T> getItems(Class<T> type) {
		Set<T> filtered = new HashSet<T>();

		for (InventoryItem item : getItems()) {
			if (type.isAssignableFrom(item.getClass())) {
				filtered.add((T) item);
			}
		}

		return filtered;
	}

	@Override
	public String toString() {
		return items.toString();
	}
}
