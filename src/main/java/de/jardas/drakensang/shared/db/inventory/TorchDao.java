package de.jardas.drakensang.shared.db.inventory;

import de.jardas.drakensang.shared.model.inventory.Torch;


public class TorchDao extends InventoryItemDao<Torch> {
    public TorchDao() {
        super(Torch.class, "Torch");
    }
}
