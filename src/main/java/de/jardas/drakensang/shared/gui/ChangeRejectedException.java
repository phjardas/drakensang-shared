/**
 * 
 */
package de.jardas.drakensang.shared.gui;

public class ChangeRejectedException extends Exception {
	private final String title;

	public ChangeRejectedException(String message, String title) {
		super(message);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}