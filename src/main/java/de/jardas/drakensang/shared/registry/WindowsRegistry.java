package de.jardas.drakensang.shared.registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public final class WindowsRegistry {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(WindowsRegistry.class);
	private static final String REGQUERY_UTIL = "reg query ";
	private static final String REGSTR_TOKEN = "REG_SZ";
	private static final String PERSONAL_FOLDER = "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\"
			+ "Explorer\\Shell Folders\" /v Personal";
	private static final String DRAKENSANG_HOME = "\"HKLM\\SOFTWARE\\DTP\\Drakensang_TRoT\""
			+ " /v target_folder";
	private static final String DRAKENSANG_DEMO_HOME = "\"HKLM\\SOFTWARE\\DTP\\Drakensang_TRoT_DEMO\""
			+ " /v target_folder";
	private static final String DRAKENSANG_LANG = "\"HKCU\\Software\\DTP\\Drakensang_TRoT\""
			+ " /v language";
	private static final String DRAKENSANG_DEMO_LANG = "\"HKCU\\Software\\DTP\\Drakensang_TRoT_DEMO\""
			+ " /v language";

	private WindowsRegistry() {
		// utility class
	}

	public static String getCurrentUserPersonalFolderPath() {
		return getRegistryValue(PERSONAL_FOLDER);
	}

	public static String getDrakensangHome() {
		final String home = getRegistryValue(DRAKENSANG_HOME);

		return home != null ? home : getRegistryValue(DRAKENSANG_DEMO_HOME);
	}

	public static String getDrakensangLanguage() {
		String language = getRegistryValue(DRAKENSANG_LANG);
		if (language != null) {
			return language;
		}

		language = getRegistryValue(DRAKENSANG_DEMO_LANG);
		if (language != null) {
			return language;
		}

		return "de";
	}

	public static String getRegistryValue(final String query) {
		try {
			Process process = Runtime.getRuntime().exec(REGQUERY_UTIL + query);
			StreamReader reader = new StreamReader(process.getInputStream());

			reader.start();
			process.waitFor();
			reader.join();

			String result = reader.getResult();
			int p = result.indexOf(REGSTR_TOKEN);

			if (p == -1) {
				LOG.debug("No value found in registry for '{}'.", query);

				return null;
			}

			final String value = result.substring(p + REGSTR_TOKEN.length())
					.trim();
			LOG.debug("Registry value '{}' resolved to '{}'.", query, value);

			return value;
		} catch (Exception e) {
			LOG.warn("Error reading from registry '" + query + "': " + e, e);

			return null;
		}
	}

	private static class StreamReader extends Thread {
		private final InputStream is;
		private final StringWriter sw = new StringWriter();

		public StreamReader(InputStream is) {
			this.is = is;
		}

		public void run() {
			try {
				int c;

				while ((c = is.read()) != -1) {
					sw.write(c);
				}
			} catch (IOException e) {
				throw new RuntimeException("Error reading from registry: " + e,
						e);
			}
		}

		public String getResult() {
			return sw.toString();
		}
	}
}
