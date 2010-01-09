package de.jardas.drakensang.shared.db.inventory;

import de.jardas.drakensang.shared.model.inventory.Item;


public class ItemDao extends InventoryItemDao<Item> {
    public ItemDao() {
        super(Item.class, "Item");
    }
}
