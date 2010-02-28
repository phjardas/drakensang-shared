package de.jardas.drakensang.shared.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.jardas.drakensang.shared.ApplicationInfo;
import de.jardas.drakensang.shared.FeatureHistory;

public class ExceptionDialog extends JDialog {
	private static ApplicationInfo applicationInfo;

	public static void setApplicationInfo(ApplicationInfo applicationInfo) {
		ExceptionDialog.applicationInfo = applicationInfo;
	}

	public ExceptionDialog(final Frame parent, Throwable throwable) {
		super(parent, "Application Failure", true);

		setLayout(new BorderLayout(5, 5));

		final StringWriter trace = new StringWriter();
		final PrintWriter writer = new PrintWriter(trace);
		throwable.printStackTrace(writer);
		writer.flush();
		writer.close();

		add(new JLabel("Unfortunately an application failure has occurred."),
				BorderLayout.NORTH);
		add(new JScrollPane(new JTextArea(trace.toString())),
				BorderLayout.CENTER);

		final JPanel toolbar = new JPanel();
		toolbar.add(new JButton(new AbstractAction(
				"Send anonymous error report") {
			public void actionPerformed(ActionEvent e) {
				try {
					final URL url = new URL(
							"http://www.jardas.de/drakensang/report.php");
					final String query = "application="
							+ URLEncoder.encode(applicationInfo
									.getApplicationName(), "utf-8")
							+ "&version="
							+ URLEncoder.encode(applicationInfo.getVersion(),
									"utf-8")
							+ "&build="
							+ URLEncoder.encode(applicationInfo.getBuild(),
									"utf-8") + "&lastFeature="
							+ FeatureHistory.getLatestFeatureId()
							+ "&stacktrace="
							+ URLEncoder.encode(trace.toString(), "utf-8")
							+ "&properties="
							+ URLEncoder.encode(getSystemProperties(), "utf-8");

					final URLConnection conn = url.openConnection();
					conn.setDoOutput(true);
					conn.setUseCaches(false);
					conn.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded; charset=utf-8");

					final OutputStreamWriter out = new OutputStreamWriter(conn
							.getOutputStream());
					out.write(query);
					out.flush();
					out.close();

					final BufferedReader in = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));

					while (true) {
						final String line = in.readLine();

						if (line == null) {
							break;
						}

						System.out.println(line);
					}

					JOptionPane.showMessageDialog(parent,
							"Your error report has been submitted.",
							"Thank you!", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane
							.showMessageDialog(
									parent,
									"Sorry, your bug report could not be submitted.\n\n"
											+ "Please visit http://www.jardas.de/drakensang2/\n"
											+ "or file your bug report by mail via philipp@jardas.de.\n\n"
											+ "Thank you.", "Sorry...",
									JOptionPane.ERROR_MESSAGE);
				}
			}
		}));
		toolbar.add(new JButton(new AbstractAction("Quit") {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		}));
		add(toolbar, BorderLayout.SOUTH);

		setSize(new Dimension(600, 800));
		setLocationRelativeTo(null);
	}

	private static String getSystemProperties() {
		final StringBuilder ret = new StringBuilder();

		final List<String> keys = new ArrayList<String>();

		for (Object key : System.getProperties().keySet()) {
			if (!"line.separator".equals(key) && !"java.class.path".equals(key)
					&& !"java.library.path".equals(key)
					&& !"sun.boot.class.path".equals(key)) {
				keys.add((String) key);
			}
		}

		Collections.sort(keys);

		for (String key : keys) {
			if (ret.length() > 0) {
				ret.append("\n");
			}

			ret.append(key + "=" + System.getProperty(key));
		}

		return ret.toString();
	}

	public static void main(String[] args) {
		System.out.println(getSystemProperties());
	}
}
