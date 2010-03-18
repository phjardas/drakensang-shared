package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.DrakensangException;
import de.jardas.drakensang.shared.model.AppearanceFeature;
import de.jardas.drakensang.shared.model.Identified;
import de.jardas.drakensang.shared.model.Race;
import de.jardas.drakensang.shared.model.Sex;

import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Array;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public abstract class AppearanceFeatureDao<F extends AppearanceFeature> {
    private static final org.slf4j.Logger LOG =
        org.slf4j.LoggerFactory.getLogger(AppearanceFeatureDao.class);
    private final String nameSingular;
    private final F[] values;
    private final Class<?extends AppearanceFeature> instanceType;

    public AppearanceFeatureDao(String nameSingular, String namePlural, String column) {
        super();
        this.nameSingular = nameSingular;

        LOG.info("Loading {}", namePlural);

        final Map<String, Info> infos = new HashMap<String, Info>();

        try {
            final PreparedStatement statement =
                Static.getConnection()
                      .prepareStatement("select AnimSet, " + column +
                    " from _Template_PC_CharWizard");
            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                final String values1 = result.getString(column);
                final String anim = result.getString("AnimSet");
                final boolean dwarven = "dwarf".equals(anim);
                final Sex sex = (dwarven || "male".equals(anim)) ? Sex.male : Sex.female;

                if (values1 == null) {
                    continue;
                }

                for (String id : values1.trim().split("\\s*[,;]\\s*")) {
                    Info info = infos.get(id);

                    if (info == null) {
                        info = new Info(id, dwarven);
                        infos.put(id, info);
                    }

                    info.addSex(sex);
                }
            }
        } catch (SQLException e) {
            throw new DrakensangException("Error loading " + namePlural + ": " + e, e);
        }

        final List<F> values = new ArrayList<F>();

        for (Info info : infos.values()) {
            final F item = create(info.getId(), info.isDwarvish(), info.getSexes());
            values.add(item);
            LOG.debug("Loaded {}: {}", nameSingular, item);
        }

        if (values.isEmpty()) {
            throw new DrakensangException("No " + namePlural + " found!");
        }

        Collections.sort(values, Identified.COMPARATOR_ASC);
        LOG.info("Loaded {} {}.", values.size(), namePlural);

        instanceType = values.iterator().next().getClass();

        @SuppressWarnings("unchecked")
        final F[] items = values.toArray((F[]) Array.newInstance(instanceType, values.size()));
        this.values = items;
    }

    protected abstract F create(String id, boolean dwarvish, Sex... sexes);

    protected F valueOf(String id) {
        for (F item : values) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Unknown " + nameSingular + ": " + id);
    }

    protected F[] values() {
        return values;
    }

    protected F[] values(Sex sex, Race race) {
        final List<F> ret = new LinkedList<F>();

        for (F item : values) {
            if (ArrayUtils.contains(item.getSexes(), sex) &&
                    (item.isDwarvish() == (race == Race.Zwerg))) {
                ret.add(item);
            }
        }

        @SuppressWarnings("unchecked")
        final F[] items = ret.toArray((F[]) Array.newInstance(instanceType, ret.size()));

        return items;
    }

    private static final class Info {
        private final String id;
        private final boolean dwarvish;
        private final Set<Sex> sexes = new HashSet<Sex>();

        public Info(String id, boolean dwarvish) {
            super();
            this.id = id;
            this.dwarvish = dwarvish;
        }

        public String getId() {
            return id;
        }

        public boolean isDwarvish() {
            return dwarvish;
        }

        public Sex[] getSexes() {
            return sexes.toArray(new Sex[sexes.size()]);
        }

        public void addSex(Sex sex) {
            sexes.add(sex);
        }
    }
}
