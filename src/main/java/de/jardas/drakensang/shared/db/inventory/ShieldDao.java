package de.jardas.drakensang.shared.db.inventory;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.jardas.drakensang.shared.DrakensangException;
import de.jardas.drakensang.shared.db.UpdateStatementBuilder;
import de.jardas.drakensang.shared.model.inventory.Shield;


public class ShieldDao extends InventoryItemDao<Shield> {
    public ShieldDao() {
        super(Shield.class, "Shield");
    }

    @Override
    public Shield load(ResultSet results) {
        final Shield shield = super.load(results);

        try {
            shield.setAttackeMod(results.getInt("WpATmod"));
            shield.setParadeMod(results.getInt("WpPAmod"));

            return shield;
        } catch (SQLException e) {
            throw new DrakensangException("Error loading ", e);
        }
    }

    @Override
    protected void appendUpdateStatements(UpdateStatementBuilder builder,
        Shield item) {
        super.appendUpdateStatements(builder, item);

        builder.append("WpATmod = ?", item.getAttackeMod());
        builder.append("WpPAmod = ?", item.getParadeMod());
    }
}
