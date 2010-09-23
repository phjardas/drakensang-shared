package de.jardas.drakensang.shared;

import de.jardas.drakensang.shared.FeatureHistory.Feature;
import de.jardas.drakensang.shared.db.LocaleOption;
import de.jardas.drakensang.shared.db.LocaleOption.LocaleNotFoundException;
import de.jardas.drakensang.shared.db.Messages;
import de.jardas.drakensang.shared.gui.ExceptionDialog;
import de.jardas.drakensang.shared.gui.LocaleChooserDialog;
import de.jardas.drakensang.shared.gui.NewFeaturesDialog;
import de.jardas.drakensang.shared.gui.WordWrap;
import de.jardas.drakensang.shared.registry.DrakensangHomeFinder;

import java.io.File;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;


public class Launcher {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(Launcher.class);
    @SuppressWarnings("rawtypes")
    private static Program program;
    private static JFrame mainFrame;
    private static ResourceBundle bundle;

    @SuppressWarnings("rawtypes")
    public static void run(final Program program) {
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    if (Launcher.program != null) {
                        throw new IllegalStateException("A program is already running!");
                    }

                    Launcher.program = program;
                    LOG.info("Bootstrapping");

                    ExceptionDialog.setApplicationInfo(program.getApplicationInfo());

                    try {
                        bundle = ResourceBundle.getBundle(program.getResourceBundleName());
                        Class.forName("org.sqlite.JDBC");

                        checkSettings();
                        Messages.init(program.getResourceBundleName());
                        FeatureHistory.init(program.getFeatureHistory());

                        final Locale locale = getUserLocale();

                        if (locale != null) {
                            setUserLocale(locale);
                            runGUI();
                        } else {
                            showLanguageChooser();
                        }
                    } catch (Exception e) {
                        handleException(e);
                    }
                }
            });
    }

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    @SuppressWarnings("unchecked")
    private static void runGUI() {
        final Feature[] features = FeatureHistory.getUnknownFeatures(Settings.getInstance());

        if (features.length > 0) {
            new NewFeaturesDialog(features, null).setVisible(true);
        }

        mainFrame = program.createMainFrame();
        mainFrame.setVisible(true);
        program.onMainFrameVisible(mainFrame);
    }

    private static void showLanguageChooser() {
        LOG.debug("Showing language chooser dialog.");
        new LocaleChooserDialog() {
            @Override
            public void onLocaleChosen(final Locale locale) {
                setVisible(false);
                setUserLocale(locale);
                runGUI();
            }

            @Override
            public void onAbort() {
                System.exit(1);
            }
        };
    }

    public static void handleException(final Exception e) {
        LOG.error("Uncaught exception: " + e, e);
        program.shutDown();

        if (mainFrame != null) {
            mainFrame.setVisible(false);
        }

        new ExceptionDialog(mainFrame, e).setVisible(true);

        LOG.info("Shutting down...");

        System.exit(1);
    }

    public static String getCurrentVersion() {
        final ResourceBundle bundle = ResourceBundle.getBundle(
                Launcher.class.getPackage().getName() + ".version");

        return bundle.getString("version");
    }

    private static void checkSettings() {
        final Settings settings = Settings.getInstance();
        LOG.debug("Testing connection to {}", Settings.getInstance().getDrakensangHome());

        if (!Messages.openConnections()) {
            Messages.closeConnections();

            final File home = DrakensangHomeFinder.findDrakensangHome();

            if (home != null) {
                settings.setDrakensangHome(home);
                settings.save();

                return;
            }
        }

        while (!Messages.openConnections()) {
            Messages.closeConnections();
            settings.setDrakensangHome(locateDrakensangHome(settings));
        }
    }

    private static Locale getUserLocale() {
        final Locale locale = Settings.getInstance().getLocale();

        if (locale != null) {
            LOG.info("Found locale in settings: {}", locale);

            return locale;
        }

        try {
            return LocaleOption.guessLocale();
        } catch (LocaleNotFoundException e) {
            LOG.warn("Locale not found: " + e, e);

            return null;
        }
    }

    public static void setUserLocale(final Locale locale) {
        LOG.debug("Setting locale to '{}'", locale);
        Locale.setDefault(locale);
        Settings.getInstance().setLocale(locale);
        Settings.getInstance().save();
        Messages.reload();

        bundle = ResourceBundle.getBundle(program.getResourceBundleName());
    }

    private static File locateDrakensangHome(final Settings settings) {
        JOptionPane.showMessageDialog(null,
            WordWrap.addNewlines(bundle.getString("drakensang.home.info")),
            bundle.getString("drakensang.home.title"),
            JOptionPane.WARNING_MESSAGE);

        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(bundle.getString("drakensang.home.title"));
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File("c:/Program Files"));

        fileChooser.removeChoosableFileFilter(fileChooser.getChoosableFileFilters()[0]);
        fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(final File f) {
                    return f.isDirectory() || f.getName().equals("drakensang.exe");
                }

                @Override
                public String getDescription() {
                    return "Drakensang (drakensang.exe)";
                }
            });

        final int result = fileChooser.showDialog(null, bundle.getString("drakensang.home.button"));

        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getParentFile();
        }

        LOG.info("No Drakensang home selected, shutting down...");
        System.exit(0);

        return null;
    }
}
