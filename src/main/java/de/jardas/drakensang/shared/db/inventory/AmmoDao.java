package de.jardas.drakensang.shared.db.inventory;

import de.jardas.drakensang.shared.model.inventory.Ammo;

public class AmmoDao extends InventoryItemDao<Ammo> {
	public AmmoDao() {
		super(Ammo.class, "Ammo");
	}
}
