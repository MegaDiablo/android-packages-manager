package com.adbhelper.adb.exceptions;

public class AdbError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4974005004014091801L;

	public AdbError() {
	}

	public AdbError(String message) {
		super(message);
	}

	public AdbError(Throwable cause) {
		super(cause);
		setStackTrace(cause.getStackTrace());
	}

	public AdbError(String message, Throwable cause) {
		super(message, cause);
		setStackTrace(cause.getStackTrace());
	}

}
