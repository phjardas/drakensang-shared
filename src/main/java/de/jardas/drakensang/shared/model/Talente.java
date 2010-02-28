package de.jardas.drakensang.shared.model;

import de.jardas.drakensang.shared.db.TalentDao;

public class Talente extends IntegerMap {
	@Override
	public String[] getKeys() {
		return TalentDao.attributes();
	}
}
