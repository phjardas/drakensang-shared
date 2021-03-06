package de.jardas.drakensang.shared.gui;

import de.jardas.drakensang.shared.Launcher;
import de.jardas.drakensang.shared.db.Messages;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.MutableComboBoxModel;


public abstract class EnumComboBox<E extends Enum<E>> extends JComboBox {
    private E current = null;

    public EnumComboBox(final E[] enumeration, final E selected) {
        super();
        setModel(new DefaultComboBoxModel());

        replaceValues(enumeration, selected);

        addItemListener(new ItemListener() {
                public void itemStateChanged(final ItemEvent evt) {
                    if (evt.getStateChange() == ItemEvent.SELECTED) {
                        @SuppressWarnings("unchecked")
                        final LocalizedEnumItem selected = (LocalizedEnumItem) evt.getItem();

                        try {
                            valueChanged(selected.getItem());
                            current = selected.getItem();
                        } catch (ChangeRejectedException e) {
                            JOptionPane.showMessageDialog(Launcher.getMainFrame(),
                                e.getMessage(),
                                e.getTitle(),
                                JOptionPane.ERROR_MESSAGE);
                            setSelected(current);
                        }
                    }
                }
            });
    }

    public void replaceValues(final E[] enumeration, final E selected) {
        removeAllItems();

        for (final E item : enumeration) {
            if (accept(item)) {
                getMutableModel().addElement(new LocalizedEnumItem(item, getLabel(toString(item))));
            }
        }

        setSelected(selected);
    }

    private MutableComboBoxModel getMutableModel() {
        return ((MutableComboBoxModel) getModel());
    }

    public void setSelected(final E selected) {
        if (selected != null) {
            for (int i = 0; i < getMutableModel().getSize(); i++) {
                @SuppressWarnings("unchecked")
                final LocalizedEnumItem el = (LocalizedEnumItem) getMutableModel().getElementAt(i);

                if (el.getItem() == selected) {
                    setSelectedIndex(i);
                    this.current = selected;
                }
            }
        }
    }

    protected String getLabel(final String key) {
        return Messages.get(key);
    }

    protected abstract void valueChanged(E item) throws ChangeRejectedException;

    protected boolean accept(final E item) {
        return toString(item) != null;
    }

    protected String toString(final E item) {
        return item.toString();
    }

    private class LocalizedEnumItem {
        private final E item;
        private final String label;

        public LocalizedEnumItem(final E item, final String label) {
            super();
            this.item = item;
            this.label = label;
        }

        public E getItem() {
            return item;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
