package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.DrakensangException;
import de.jardas.drakensang.shared.model.Identified;

import java.lang.reflect.Array;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class EnumerationDao<F extends Identified> {
    private static final org.slf4j.Logger LOG =
        org.slf4j.LoggerFactory.getLogger(AppearanceFeatureDao.class);
    private final F[] values;
    private final Class<?extends Identified> instanceType;

    public EnumerationDao(Class<?extends Identified> instanceType, String query) {
        super();
        this.instanceType = instanceType;

        final List<F> values = new ArrayList<F>();

        try {
            final PreparedStatement statement = Static.getConnection().prepareStatement(query);
            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                final F item = create(result);

                if (item != null) {
                    values.add(item);
                    LOG.debug("Loaded {}", item);
                }
            }
        } catch (SQLException e) {
            throw new DrakensangException("Error loading items for " +
                instanceType.getSimpleName() + ": " + e, e);
        }

        Collections.sort(values, Identified.COMPARATOR_ASC);
        LOG.info("Loaded {} items for {}", values.size(), instanceType.getSimpleName());

        @SuppressWarnings("unchecked")
        final F[] items = values.toArray((F[]) Array.newInstance(instanceType, values.size()));
        this.values = items;
    }

    protected abstract F create(ResultSet result) throws SQLException;

    protected F valueOf(String id) {
        for (F item : values) {
            if (isItem(item, id)) {
                return item;
            }
        }

        throw new IllegalArgumentException("No " + instanceType.getSimpleName() +
            " known for ID: " + id);
    }

    protected boolean isItem(F item, String id) {
        return item.getId().equals(id);
    }

    protected F[] values() {
        return values;
    }
}
