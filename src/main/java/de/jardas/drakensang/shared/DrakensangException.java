package de.jardas.drakensang.shared;

public class DrakensangException extends RuntimeException {
	public DrakensangException() {
		super();
	}

	public DrakensangException(String message, Throwable cause) {
		super(message, cause);
	}

	public DrakensangException(String message) {
		super(message);
	}

	public DrakensangException(Throwable cause) {
		super(cause);
	}
}
