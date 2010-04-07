package de.jardas.drakensang.shared;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Locale;
import java.util.Properties;


public abstract class Settings {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(Settings.class);
    private static File settingsDirectory;
    private static File settingsFile;
    private static Settings instance;
    private static Class<?extends Settings> settingsClass;
    private File drakensangHome;
    private int latestKnownFeature;
    private Locale locale;

    public static synchronized void init(String settingsDirectory,
        Class<?extends Settings> settingsClass) {
        Settings.settingsDirectory = new File(new File(System.getProperty("user.home")),
                settingsDirectory);
        settingsFile = new File(Settings.settingsDirectory, "settings.properties");
        Settings.settingsClass = settingsClass;
    }

    public static File getSettingsDirectory() {
        return settingsDirectory;
    }

    public static synchronized Settings getInstance() {
        if (settingsDirectory == null) {
            throw new RuntimeException("Settings were not initialized!");
        }

        if (instance == null) {
            instance = load();
        }

        return instance;
    }

    protected void readProperties(Properties props) {
        if (props.get("drakensang.home") != null) {
            setDrakensangHome(new File(props.getProperty("drakensang.home")));
        }

        if (props.get("latestKnownFeature") != null) {
            setLatestKnownFeature(Integer.valueOf(props.getProperty("latestKnownFeature")));
        }

        if (props.get("locale.language") != null) {
            final String language = props.getProperty("locale.language");
            final String country = props.getProperty("locale.country");
            final String variant = props.getProperty("locale.variant");
            setLocale(new Locale(language, country, variant));
        }
    }

    protected void writeProperties(Properties props) {
        if (getDrakensangHome() != null) {
            props.setProperty("drakensang.home", getDrakensangHome().getAbsolutePath());
        }

        props.setProperty("latestKnownFeature", String.valueOf(getLatestKnownFeature()));

        if (getLocale() != null) {
            props.setProperty("locale.language", getLocale().getLanguage());
            props.setProperty("locale.country", getLocale().getCountry());
            props.setProperty("locale.variant", getLocale().getVariant());
        }
    }

    protected void initialize() {
        latestKnownFeature = FeatureHistory.getLatestFeatureId();
    }

    public File getDrakensangHome() {
        return drakensangHome;
    }

    public void setDrakensangHome(File drakensangHome) {
        this.drakensangHome = drakensangHome;
    }

    public int getLatestKnownFeature() {
        return latestKnownFeature;
    }

    public void setLatestKnownFeature(int latestKnownFeature) {
        this.latestKnownFeature = latestKnownFeature;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public synchronized void save() {
        final Properties props = new Properties();
        writeProperties(props);

        try {
            settingsFile.getParentFile().mkdirs();
            props.store(new FileOutputStream(settingsFile), null);
            LOG.debug("Settings saved to {}.", settingsFile);
        } catch (IOException e) {
            LOG.error("Error writing settings to " + settingsFile + ": " + e, e);
        }
    }

    private static synchronized Settings load() {
        LOG.debug("Loading settings from " + settingsFile);

        final Settings settings = createSettingsInstance();

        try {
            final Properties props = new Properties();
            final FileInputStream reader = new FileInputStream(settingsFile);
            props.load(reader);
            reader.close();
            settings.readProperties(props);
        } catch (IOException e) {
            LOG.info("No settings found at {}: {}", settingsFile, e);
            settings.initialize();
        }

        LOG.debug("Loaded settings: {}", settings);

        return settings;
    }

    private static Settings createSettingsInstance() {
        try {
            return settingsClass.newInstance();
        } catch (InstantiationException e) {
            throw new DrakensangException("Error instanciating settings class: " + e, e);
        } catch (IllegalAccessException e) {
            throw new DrakensangException("Error instanciating settings class: " + e, e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this).toString();
    }
}
