package de.jardas.drakensang.shared.db.inventory;

import de.jardas.drakensang.shared.model.inventory.Jewelry;


public class JewelryDao extends InventoryItemDao<Jewelry> {
    public JewelryDao() {
        super(Jewelry.class, "Jewelry");
    }
}
