package de.jardas.drakensang.shared;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationInfo {
	private final String applicationName;
	private final String version;
	private final String build;

	private ApplicationInfo(String applicationName, String version, String build) {
		super();
		this.applicationName = applicationName;
		this.version = version;
		this.build = build;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getVersion() {
		return version;
	}

	public String getBuild() {
		return build;
	}

	public static ApplicationInfo load(InputStream in) {
		final Properties props = new Properties();

		try {
			props.load(in);
		} catch (IOException e) {
			throw new DrakensangException("Error loading application info: "
					+ e, e);
		}

		return new ApplicationInfo(props.getProperty("name"), props
				.getProperty("version"), props.getProperty("build"));
	}
}
