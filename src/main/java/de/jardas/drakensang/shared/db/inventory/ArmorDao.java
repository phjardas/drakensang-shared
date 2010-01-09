package de.jardas.drakensang.shared.db.inventory;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.jardas.drakensang.shared.DrakensangException;
import de.jardas.drakensang.shared.db.UpdateStatementBuilder;
import de.jardas.drakensang.shared.model.inventory.Armor;

public class ArmorDao extends InventoryItemDao<Armor> {
	public ArmorDao() {
		super(Armor.class, "Armor");
	}

	@Override
	public Armor load(ResultSet results) {
		final Armor armor = super.load(results);

		try {
			armor.setRuestungKopf(results.getInt("RsKo"));
			armor.setRuestungBrust(results.getInt("RsBr"));
			armor.setRuestungRuecken(results.getInt("RsRu"));
			armor.setRuestungBauch(results.getInt("RsBa"));
			armor.setRuestungArmLinks(results.getInt("RsLA"));
			armor.setRuestungArmRechts(results.getInt("RsRA"));
			armor.setRuestungBeinLinks(results.getInt("RsLB"));
			armor.setRuestungBeinRechts(results.getInt("RsRB"));

			return armor;
		} catch (SQLException e) {
			throw new DrakensangException("Error loading ", e);
		}
	}

	@Override
	protected void appendUpdateStatements(UpdateStatementBuilder builder,
			Armor item) {
		super.appendUpdateStatements(builder, item);

		builder.append("RsKo = ?", item.getRuestungKopf());
		builder.append("RsBr = ?", item.getRuestungBrust());
		builder.append("RsRu = ?", item.getRuestungRuecken());
		builder.append("RsBa = ?", item.getRuestungBauch());
		builder.append("RsLA = ?", item.getRuestungArmLinks());
		builder.append("RsRA = ?", item.getRuestungArmRechts());
		builder.append("RsLB = ?", item.getRuestungBeinLinks());
		builder.append("RsRB = ?", item.getRuestungBeinRechts());
	}
}
