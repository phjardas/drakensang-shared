package de.jardas.drakensang.shared.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.jardas.drakensang.shared.db.Messages;
import de.jardas.drakensang.shared.model.Regenerating;

public class RegeneratingPanel extends JPanel {
	public RegeneratingPanel(String name, final Regenerating regenerating) {
		if (name != null) {
			setBorder(BorderFactory.createTitledBorder(Messages.get(name)));
		}

		setLayout(new GridBagLayout());
		int row = 0;

		final JSpinner value = new JSpinner(new SpinnerNumberModel(regenerating
				.getCurrentValue(), 0, 1000, 1));
		value.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				final int newValue = ((Number) value.getValue()).intValue();
				if (newValue > regenerating.getMaxValue()) {
					value.setValue(regenerating.getMaxValue());
				} else {
					regenerating.setCurrentValue(newValue);
				}
			}
		});
		addInput("reg.value", value, row++);

		final JSpinner bonus = new JSpinner(new SpinnerNumberModel(regenerating
				.getBonus(), -100, 100, 1));
		bonus.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				regenerating.setBonus(((Number) bonus.getValue()).intValue());

			}
		});
		addInput("reg.bonus", bonus, row++);

		final JLabel max = new JLabel(String
				.valueOf(regenerating.getMaxValue()));
		addInput("reg.max", max, row++);

		final JSpinner regeneration = new JSpinner(new SpinnerNumberModel(
				regenerating.getRegenerationAmount(), 0, 100, 1));
		regeneration.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				regenerating.setRegenerationAmount(((Number) regeneration
						.getValue()).intValue());
			}
		});
		addInput("reg.reg", regeneration, row++);

		final JSpinner regFreq = new JSpinner(new SpinnerNumberModel(
				regenerating.getRegenerationFrequency(), 0, 100, 1));
		regFreq.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				regenerating.setRegenerationFrequency(((Number) regFreq
						.getValue()).intValue());

			}
		});
		addInput("reg.regfreq", regFreq, row++);

		final JSpinner regFreqCombat = new JSpinner(new SpinnerNumberModel(
				regenerating.getRegenerationFrequencyCombat(), 0, 100, 1));
		regFreqCombat.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				regenerating
						.setRegenerationFrequencyCombat(((Number) regFreqCombat
								.getValue()).intValue());

			}
		});
		addInput("reg.regfreqcombat", regFreqCombat, row++);

		regenerating.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("maxValue".equals(evt.getPropertyName())) {
					max.setText(evt.getNewValue().toString());
				} else if ("currentValue".equals(evt.getPropertyName())) {
					value.setValue(evt.getNewValue());
				}
			}
		});
	}

	private void addInput(String label, JComponent input, int row) {
		add(new InfoLabel(label), new GridBagConstraints(0, row, 1, 1, 0, 0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				new Insets(3, 6, 3, 6), 0, 0));

		add(input, new GridBagConstraints(1, row, 1, 1, 0, 0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(3, 6, 3, 6), 0, 0));
	}
}
