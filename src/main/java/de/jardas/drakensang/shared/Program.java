package de.jardas.drakensang.shared;

import java.io.InputStream;

import javax.swing.JFrame;

public interface Program<F extends JFrame> {
	F createMainFrame();

	void onMainFrameVisible(F mainFrame);

	void shutDown();

	String getResourceBundleName();

	InputStream getFeatureHistory();
}
