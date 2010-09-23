package de.jardas.drakensang.shared.gui;

import de.jardas.drakensang.shared.Launcher;
import de.jardas.drakensang.shared.db.Messages;
import de.jardas.drakensang.shared.model.Identified;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.MutableComboBoxModel;


public abstract class IdentifiedComboBox<I extends Identified> extends JComboBox {
    private I current = null;

    public IdentifiedComboBox(final I[] values, final I selected) {
        super();
        setModel(new DefaultComboBoxModel());

        replaceValues(values, selected);

        addItemListener(new ItemListener() {
                public void itemStateChanged(final ItemEvent evt) {
                    if (evt.getStateChange() == ItemEvent.SELECTED) {
                        @SuppressWarnings("unchecked")
                        final LocalizedItem selected = (LocalizedItem) evt.getItem();

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

    public void replaceValues(final I[] enumeration, final I selected) {
        removeAllItems();

        for (final I item : enumeration) {
            if (accept(item)) {
                getMutableModel().addElement(new LocalizedItem(item, getLabel(item.getId())));
            }
        }

        setSelected(selected);
    }

    private MutableComboBoxModel getMutableModel() {
        return ((MutableComboBoxModel) getModel());
    }

    public void setSelected(final I selected) {
        if (selected != null) {
            for (int i = 0; i < getMutableModel().getSize(); i++) {
                @SuppressWarnings("unchecked")
                final LocalizedItem el = (LocalizedItem) getMutableModel().getElementAt(i);

                if (el.getItem() == selected) {
                    setSelectedIndex(i);
                    this.current = selected;
                }
            }
        }
    }

    protected String getLabel(final String id) {
        return Messages.get(id);
    }

    protected abstract void valueChanged(I item) throws ChangeRejectedException;

    protected boolean accept(final I item) {
        return (item != null) && (item.getId() != null);
    }

    private class LocalizedItem {
        private final I item;
        private final String label;

        public LocalizedItem(final I item, final String label) {
            super();
            this.item = item;
            this.label = label;
        }

        public I getItem() {
            return item;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
