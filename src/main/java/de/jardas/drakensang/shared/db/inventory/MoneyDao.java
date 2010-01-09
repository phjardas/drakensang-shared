package de.jardas.drakensang.shared.db.inventory;

import de.jardas.drakensang.shared.model.inventory.Money;


public class MoneyDao extends InventoryItemDao<Money> {
    public MoneyDao() {
        super(Money.class, "Money");
    }
}
