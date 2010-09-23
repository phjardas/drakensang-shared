package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.Settings;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class Messages {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(Messages.class);
    private static final String[] CONNECTION_CANDIDATES = {
            "export/db/locale.db4", "export/db/localea1.db4",
        };
    private static final Map<String, String> CACHE = new HashMap<String, String>();
    private static String bundleName;
    private static List<Connection> connections = Collections.synchronizedList(
            new LinkedList<Connection>());

    public static void init(final String bundleName) {
        Messages.bundleName = bundleName;
        reload();
    }

    public static void reload() {
        final Locale locale = Locale.getDefault();

        LOG.info("Loading resources for locale '{}' from '{}'.", locale, bundleName);

        final ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
        final Enumeration<String> keys = bundle.getKeys();

        while (keys.hasMoreElements()) {
            final String key = keys.nextElement();
            final String value = bundle.getString(key);

            LOG.debug("Caching message '{}' with value '{}'.", key, value);

            cache(key, value);
        }
    }

    private static void cache(final String key, final String value) {
        CACHE.put(key.toLowerCase(), value);
    }

    public static String get(final String key) {
        String ret = "";

        if (key != null) {
            try {
                ret = getRequired(key);
            } catch (MissingResourceException e) {
                LOG.warn("No localization found for '" + key + "': " + e);

                ret = "!!!" + key + "!!!";
            }
        }

        return ret;
    }

    public static String getRequired(final String key) {
        String value = CACHE.get(key.toLowerCase());

        if (value != null) {
            return value;
        }

        value = getFromDb(key);
        cache(key, value);

        if (value.startsWith("Obsolete")) {
            throw new MissingResourceException("Obsolete translation for '" + key + "'",
                Messages.class.getName(),
                key);
        }

        return value;
    }

    public static String getFromDb(final String key) {
        try {
            LOG.debug("Loading LocaText from _Locale where LocaId = '{}'.", new Object[] { key });

            for (final Connection conn : connections) {
                final PreparedStatement statement = conn.prepareStatement(
                        "select LocaText from _Locale where LocaId = ?");
                statement.setString(1, key);

                final ResultSet result = statement.executeQuery();

                if (result.next()) {
                    return result.getString(1);
                }
            }

            throw new MissingResourceException("No localization found for '" + key + "'.",
                Messages.class.getName(),
                key);
        } catch (SQLException e) {
            throw new MissingResourceException(
                "Error looking up localized value for '" + key + "': " + e,
                Messages.class.getName(),
                key);
        }
    }

    public static boolean openConnections() {
        closeConnections();

        final File home = Settings.getInstance().getDrakensangHome();

        synchronized (connections) {
            for (final String filename : CONNECTION_CANDIDATES) {
                final File file = new File(home, filename);

                if (!file.canRead()) {
                    LOG.info("Skipping non-existant localization file: {}", file.getAbsolutePath());

                    continue;
                }

                final String url = "jdbc:sqlite:/" + file.getAbsolutePath();
                LOG.info("Trying to open localization connection to {}", url);

                try {
                    connections.add(DriverManager.getConnection(url));
                    LOG.info("Established localization connection to {}", url);
                } catch (SQLException e) {
                    LOG.info("Error opening localization connection to {}: {}", url, e);
                }
            }

            if (connections.isEmpty()) {
                LOG.info(
                    "Could not open localization connection to any candidate: " +
                    Arrays.toString(CONNECTION_CANDIDATES));

                return false;
            }

            return true;
        }
    }

    public static void closeConnections() {
        synchronized (connections) {
            if (!connections.isEmpty()) {
                LOG.info("Resetting localization connections.");

                for (final Connection conn : connections) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        LOG.warn("Error closing localization connection: " + e, e);
                    }
                }

                connections.clear();
            }
        }
    }
}
