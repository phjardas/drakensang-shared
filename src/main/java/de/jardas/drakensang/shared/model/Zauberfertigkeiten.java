package de.jardas.drakensang.shared.model;

import de.jardas.drakensang.shared.db.ZauberfertigkeitDao;

public class Zauberfertigkeiten extends IntegerMap {
	@Override
	public String[] getKeys() {
		return ZauberfertigkeitDao.attributes();
	}
}
