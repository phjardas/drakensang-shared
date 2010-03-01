package de.jardas.drakensang.shared.registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import de.jardas.drakensang.shared.DrakensangException;

public final class WindowsRegistry {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(WindowsRegistry.class);
	private static final String REGQUERY_UTIL = "reg query ";
	private static final String REGSTR_TOKEN = "REG_SZ";
	private static final String REGSTR_EXPAND_TOKEN = "REG_EXPAND_SZ";
	private static final String PERSONAL_FOLDER = "\"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\"
			+ "Explorer\\User Shell Folders\" /v Personal";
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
		final String reg = getRegistryValue(PERSONAL_FOLDER);

		if (reg == null) {
			throw new DrakensangException("Could not locate personal folder.");
		}

		return reg;
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
			final Process process = Runtime.getRuntime().exec(
					REGQUERY_UTIL + query);
			final StreamReader reader = new StreamReader(process
					.getInputStream());

			reader.start();
			process.waitFor();
			reader.join();

			final String result = reader.getResult();
			final String value = extractValue(result);

			if (value != null) {
				LOG
						.debug("Registry value '{}' resolved to '{}'.", query,
								value);
			} else {
				LOG.info("No value found in registry for '{}'.", query);
			}

			return value;
		} catch (Exception e) {
			LOG.warn("Error reading from registry '" + query + "': " + e, e);

			return null;
		}
	}

	private static String extractValue(final String response) {
		int p = response.indexOf(REGSTR_EXPAND_TOKEN);

		if (p >= 0) {
			return expand(response.substring(p + REGSTR_EXPAND_TOKEN.length())
					.trim());
		}

		p = response.indexOf(REGSTR_TOKEN);

		if (p < 0) {
			return null;
		}

		return response.substring(p + REGSTR_TOKEN.length()).trim();
	}

	private static String expand(String value) {
		LOG.debug("Expanding variables in {}", value);
		final StringBuilder buffer = new StringBuilder();
		final StringBuilder variable = new StringBuilder();
		final int len = value.length();
		int i = 0;
		boolean inVariable = false;

		while (i < len) {
			final char c = value.charAt(i);

			if (inVariable) {
				if (c == '%') {
					final String var = variable.toString();
					LOG.debug("Expanding variable {}", var);
					final String resolved = System.getenv(var);

					if (resolved == null) {
						throw new IllegalArgumentException(
								"Unknown environment variable '" + var
										+ "' in registry value '" + value + "'");
					}

					buffer.append(resolved);
					inVariable = false;
				} else {
					variable.append(c);
				}
			} else {
				if (c == '%') {
					variable.setLength(0);
					inVariable = true;
				} else {
					buffer.append(c);
				}
			}

			i++;
		}

		return buffer.toString();
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
