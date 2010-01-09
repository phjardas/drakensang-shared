package de.jardas.drakensang.shared.db.inventory;

import de.jardas.drakensang.shared.model.inventory.Key;


public class KeyDao extends InventoryItemDao<Key> {
    public KeyDao() {
        super(Key.class, "Key");
    }
}
