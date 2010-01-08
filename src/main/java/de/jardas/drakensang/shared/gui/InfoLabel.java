package de.jardas.drakensang.shared.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.MissingResourceException;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import de.jardas.drakensang.shared.Launcher;
import de.jardas.drakensang.shared.db.Messages;

public class InfoLabel extends JComponent {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(InfoLabel.class);
	private JLabel nameLabel;

	public InfoLabel(String key) {
		this(key, null, null, true);
	}

	public InfoLabel(String key, boolean localize) {
		this(key, null, null, localize);
	}

	public InfoLabel(String key, String infoKey) {
		this(key, infoKey, true);
	}

	public InfoLabel(String key, String infoKey, boolean localize) {
		this(key, infoKey, null, localize);
	}

	public InfoLabel(String key, String infoKey, final Icon icon) {
		this(key, infoKey, icon, true);
	}

	public InfoLabel(String key, String infoKey, final Icon icon,
			boolean localize) {
		super();

		setLayout(new GridBagLayout());

		final String name = localize ? Messages.get(key) : key;

		if (key != null) {
			nameLabel = new JLabel(name);
			add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
					GridBagConstraints.WEST, GridBagConstraints.NONE,
					new Insets(0, 0, 0, 0), 0, 0));
		}

		if (infoKey != null) {
			try {
				final String info = WordWrap.addNewlines(localize ? Messages
						.getRequired(infoKey) : infoKey);

				JComponent anchor = new JLabel("?");
				anchor.setForeground(Color.BLUE);
				anchor
						.setCursor(Cursor
								.getPredefinedCursor(Cursor.HAND_CURSOR));
				anchor.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						JOptionPane.showMessageDialog(Launcher.getMainFrame(),
								info, name, JOptionPane.INFORMATION_MESSAGE,
								icon);
					}
				});

				add(anchor, new GridBagConstraints(1, 0, 1, 1, 0, 0,
						GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(0, 3, 0, 0), 0, 0));
			} catch (MissingResourceException e) {
				LOG.warn("Missing info for '" + infoKey + "'.");
			}
		}
	}

	public JLabel getNameLabel() {
		return nameLabel;
	}
}
