package de.jardas.drakensang.shared.gui;

import javax.swing.SpinnerNumberModel;

public class TalentSpinnerModel extends SpinnerNumberModel {
	private static final int UNAVAILABLE = -500;
	private final String key;

	private TalentSpinnerModel(String key, int value, int maxValue) {
		super(value, UNAVAILABLE, maxValue, 1);

		this.key = key;
	}

	public static TalentSpinnerModel create(String key, int value, int maxValue) {
		try {
			return new TalentSpinnerModel(key, value, maxValue);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Value " + value + " for " + key
					+ " must be between " + UNAVAILABLE + " and " + maxValue);
		}
	}

	public Object getNextValue() {
		final int value = getNumber().intValue();

		if (value < 0) {
			return 0;
		}

		return super.getNextValue();
	}

	public Object getPreviousValue() {
		final int value = getNumber().intValue();

		if (value < 0) {
			return null;
		}

		if (value == 0) {
			return UNAVAILABLE;
		}

		return super.getPreviousValue();
	}

	public void setValue(Object value) {
		try {
			super.setValue(value);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Value " + value + " for " + key
					+ " must be between " + getMinimum() + " and "
					+ getMaximum());
		}
	}
}
