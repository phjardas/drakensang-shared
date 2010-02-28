package de.jardas.drakensang.shared.gui.character;

import de.jardas.drakensang.shared.db.Static;
import de.jardas.drakensang.shared.gui.IntegerMapPanel;
import de.jardas.drakensang.shared.model.Attribute;

public class AttributePanel extends IntegerMapPanel<Attribute> {
	public AttributePanel() {
		setDefaultSpinnerMin(0);
		setDefaultSpinnerMax(500);
	}

	@Override
	protected String getInfoKey(String key) {
		return Static.get("Description", key, "Id", "_Template_CharAttr");
	}
}
