package de.jardas.drakensang.shared.db.inventory;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.jardas.drakensang.shared.DrakensangException;
import de.jardas.drakensang.shared.db.UpdateStatementBuilder;
import de.jardas.drakensang.shared.model.Schaden;
import de.jardas.drakensang.shared.model.inventory.Weapon;

public class WeaponDao extends InventoryItemDao<Weapon> {
	public WeaponDao() {
		super(Weapon.class, "Weapon");
	}

	@Override
	public Weapon load(ResultSet results) {
		final Weapon weapon = super.load(results);

		try {
			weapon.setSchaden(new Schaden(results.getInt("WpW6"), results
					.getInt("WpW6plus")));
			weapon.setAttackeMod(results.getInt("WpATmod"));
			weapon.setParadeMod(results.getInt("WpPAmod"));

			return weapon;
		} catch (SQLException e) {
			throw new DrakensangException("Error loading ", e);
		}
	}

	@Override
	protected void appendUpdateStatements(UpdateStatementBuilder builder,
			Weapon item) {
		super.appendUpdateStatements(builder, item);

		builder.append("WpW6 = ?", item.getSchaden().getDiceMultiplier());
		builder.append("WpW6plus = ?", item.getSchaden().getAddition());
		builder.append("WpATmod = ?", item.getAttackeMod());
		builder.append("WpPAmod = ?", item.getParadeMod());
	}
}
