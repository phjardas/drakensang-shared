package de.jardas.drakensang.shared.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import de.jardas.drakensang.shared.DrakensangException;
import de.jardas.drakensang.shared.db.Messages;
import de.jardas.drakensang.shared.model.IntegerMap;

public abstract class IntegerMapPanel<M extends IntegerMap> extends JPanel {
	private final Map<String, JComponent> labels = new HashMap<String, JComponent>();
	private final Map<String, JComponent> fields = new HashMap<String, JComponent>();
	private final Map<String, JComponent> specials = new HashMap<String, JComponent>();
	private M values;
	private int defaultSpinnerMin = -1000;
	private int defaultSpinnerMax = 1000;

	protected void update() {
		labels.clear();
		fields.clear();
		specials.clear();

		removeAll();
		setLayout(new GridBagLayout());

		addFields();
	}

	protected boolean isVisible(String key) {
		return true;
	}

	protected void addFields() {
		List<String> keys = new ArrayList<String>();

		for (String key : Arrays.asList(values.getKeys())) {
			if (isVisible(key)) {
				keys.add(key);
			}
		}

		sortKeys(keys);

		JComponent parent = null;
		String currentGroupKey = null;
		Status status = new Status();
		int parentRow = 0;

		for (String key : keys) {
			String groupKey = getGroupKey(key);

			if (isGrouped()
					&& ((parent == null) || (currentGroupKey == null) || !currentGroupKey
							.equals(groupKey))) {
				parent = new JPanel();
				parent.setLayout(new GridBagLayout());
				parent.setBorder(BorderFactory.createTitledBorder(Messages
						.get(groupKey)));
				status = new Status();
				addGroup(parent, parentRow++);
				currentGroupKey = groupKey;
			}

			int value = values.get(key);
			addField(key, value, isGrouped() ? parent : this, status,
					createSpecial(key));
			status.advance();
		}

		final int spacerCol = isGrouped() ? getColumnCount() : 2;
		final int spacerRow = isGrouped() ? getRowCount(parentRow) : status
				.getRow();
		add(new JLabel(), new GridBagConstraints(spacerCol, spacerRow, 1, 1, 1,
				1, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
	}

	protected int getColumnCount() {
		return 1;
	}

	protected int getRowCount(int index) {
		return index;
	}

	protected void addGroup(JComponent group, int index) {
		add(group, new GridBagConstraints(0, index, 1, 1, 0, 0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
	}

	protected JComponent createSpecial(String key) {
		return null;
	}

	protected void sortKeys(List<String> keys) {
		Collections.sort(keys, getKeyComparator());
	}

	protected Comparator<String> getKeyComparator() {
		return new Comparator<String>() {
			private final Collator collator = Collator.getInstance();

			public int compare(String s0, String s1) {
				if (isGrouped()) {
					String g0 = Messages.get(getGroupKey(s0));
					String g1 = Messages.get(getGroupKey(s1));
					int groupCompare = collator.compare(g0, g1);

					if (groupCompare != 0) {
						return groupCompare;
					}
				}

				return collator.compare(getName(s0), getName(s1));
			}
		};
	}

	protected boolean isGrouped() {
		return false;
	}

	protected String getGroupKey(String key) {
		return null;
	}

	protected void addField(final String key, int value, JComponent parent,
			Status status, JComponent special) {
		final JComponent label = createLabel(key);
		final JComponent spinner = createField(key, value);
		Insets insets = new Insets(3, 6, 3, 6);
		parent.add(label, new GridBagConstraints(status.getColumn(), status
				.getRow(), 1, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, insets, 0, 0));
		parent.add(spinner, new GridBagConstraints(status.getColumn() + 1,
				status.getRow(), 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, insets, 0, 0));

		if (special != null) {
			parent.add(special, new GridBagConstraints(status.getColumn() + 2,
					status.getRow(), 1, 1, 0, 0, GridBagConstraints.CENTER,
					GridBagConstraints.HORIZONTAL, insets, 0, 0));
		}

		labels.put(key, label);
		fields.put(key, spinner);
		specials.put(key, special);
	}

	protected JComponent createField(final String key, int value) {
		try {
			final SpinnerModel model = createSpinnerModel(key, value);
			final JSpinner spinner = new JSpinner(model);

			spinner.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					final int val = ((Number) spinner.getValue()).intValue();
					handleChange(key, val);
				}
			});

			return spinner;
		} catch (IllegalArgumentException e) {
			throw new DrakensangException("Error adding field '" + key
					+ "' with value " + value + ": " + e, e);
		}
	}

	protected SpinnerModel createSpinnerModel(String key, int value) {
		return new SpinnerNumberModel(value, defaultSpinnerMin,
				defaultSpinnerMax, 1);
	}

	protected InfoLabel createLabel(final String key) {
		return new InfoLabel(getLocalKey(key), getInfoKey(key));
	}

	protected String getName(final String key) {
		String localKey = getLocalKey(key);

		return Messages.get(localKey);
	}

	protected String getLocalKey(String key) {
		return key;
	}

	protected String getInfoKey(String key) {
		return null;
	}

	protected void handleChange(String key, int value) {
		values.set(key, value);
	}

	public M getValues() {
		return values;
	}

	public void setValues(M values) {
		if (values == this.values) {
			return;
		}

		this.values = values;
		update();
	}

	public Map<String, JComponent> getFields() {
		return this.fields;
	}

	public Map<String, JComponent> getLabels() {
		return this.labels;
	}

	public Map<String, JComponent> getSpecials() {
		return this.specials;
	}

	public void setDefaultSpinnerMin(int defaultSpinnerMin) {
		this.defaultSpinnerMin = defaultSpinnerMin;
	}

	public void setDefaultSpinnerMax(int defaultSpinnerMax) {
		this.defaultSpinnerMax = defaultSpinnerMax;
	}

	private class Status {
		private int column = 0;
		private int row = 0;

		public int getColumn() {
			return column;
		}

		public int getRow() {
			return row;
		}

		public void advance() {
			column += 2;

			if (column >= 2) {
				column = 0;
				row++;
			}
		}
	}
}
