package de.jardas.drakensang.shared;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import de.jardas.drakensang.shared.FeatureHistory.Feature;
import de.jardas.drakensang.shared.db.LocaleOption;
import de.jardas.drakensang.shared.db.Messages;
import de.jardas.drakensang.shared.db.LocaleOption.LocaleNotFoundException;
import de.jardas.drakensang.shared.gui.ExceptionDialog;
import de.jardas.drakensang.shared.gui.LocaleChooserDialog;
import de.jardas.drakensang.shared.gui.NewFeaturesDialog;
import de.jardas.drakensang.shared.gui.WordWrap;
import de.jardas.drakensang.shared.registry.DrakensangHomeFinder;

public class Launcher implements Runnable {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(Launcher.class);
	private final Program program;
	private ResourceBundle bundle;

	public Launcher(Program program) {
		super();
		this.program = program;
	}

	public void run() {
		LOG.info("Bootstrapping");

		try {
			Class.forName("org.sqlite.JDBC");

			checkSettings();
			Messages.init(program.getResourceBundleName());
			FeatureHistory.init(program.getFeatureHistory());

			final Locale locale = getUserLocale();

			if (locale != null) {
				setUserLocale(locale);
				showMainFrame();
			} else {
				showLanguageChooser();
			}
		} catch (Exception e) {
			handleException(e);
		}
	}

	private void showMainFrame() {
		final Feature[] features = FeatureHistory.getUnknownFeatures(Settings
				.getInstance());

		if (features.length > 0) {
			new NewFeaturesDialog(features, null).setVisible(true);
		}

		program.showMainFrame();
	}

	private void showLanguageChooser() {
		LOG.debug("Showing language chooser dialog.");
		new LocaleChooserDialog() {
			@Override
			public void onLocaleChosen(Locale locale) {
				setVisible(false);
				setUserLocale(locale);
				showMainFrame();
			}

			@Override
			public void onAbort() {
				System.exit(1);
			}
		};
	}

	public void handleException(Exception e) {
		LOG.error("Uncaught exception: " + e, e);
		program.shutDown();

		new ExceptionDialog(program.getMainFrame(), e).setVisible(true);

		LOG.info("Shutting down...");

		System.exit(1);
	}

	public String getCurrentVersion() {
		ResourceBundle bundle = ResourceBundle.getBundle(Launcher.class
				.getPackage().getName()
				+ ".version");

		return bundle.getString("version");
	}

	private void checkSettings() {
		final Settings settings = Settings.getInstance();
		LOG.debug("Testing connection to "
				+ Settings.getInstance().getDrakensangHome());

		if (!Messages.testConnection()) {
			Messages.resetConnection();

			final File home = DrakensangHomeFinder.findDrakensangHome();

			if (home != null) {
				settings.setDrakensangHome(home);
				settings.save();

				return;
			}
		}

		while (!Messages.testConnection()) {
			Messages.resetConnection();
			settings.setDrakensangHome(locateDrakensangHome(settings));
		}
	}

	private Locale getUserLocale() {
		final Locale locale = Settings.getInstance().getLocale();

		if (locale != null) {
			LOG.info("Found locale in settings: " + locale);

			return locale;
		}

		try {
			return LocaleOption.guessLocale();
		} catch (LocaleNotFoundException e) {
			LOG.warn("Locale not found: " + e, e);

			return null;
		}
	}

	public void setUserLocale(Locale locale) {
		LOG.debug("Setting locale to '" + locale + "'.");
		Locale.setDefault(locale);
		Settings.getInstance().setLocale(locale);
		Settings.getInstance().save();
		Messages.reload();

		bundle = ResourceBundle.getBundle(program.getResourceBundleName());
	}

	private File locateDrakensangHome(Settings settings) {
		JOptionPane.showMessageDialog(null, WordWrap.addNewlines(bundle
				.getString("drakensang.home.info")), bundle
				.getString("drakensang.home.title"),
				JOptionPane.WARNING_MESSAGE);

		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(bundle.getString("drakensang.home.title"));
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File("c:/Program Files"));

		fileChooser.removeChoosableFileFilter(fileChooser
				.getChoosableFileFilters()[0]);
		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().equals("drakensang.exe");
			}

			public String getDescription() {
				return "Drakensang (drakensang.exe)";
			}
		});

		int result = fileChooser.showDialog(null, bundle
				.getString("drakensang.home.button"));

		if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile().getParentFile();
		}

		LOG.info("No Drakensang home selected, shutting down...");
		System.exit(0);

		return null;
	}
}
