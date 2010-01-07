package de.jardas.drakensang.shared;

import java.io.InputStream;

import javax.swing.JFrame;

public interface Program {
	void showMainFrame();

	void shutDown();

	JFrame getMainFrame();

	String getResourceBundleName();

	InputStream getFeatureHistory();
}
