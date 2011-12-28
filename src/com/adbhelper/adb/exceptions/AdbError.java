package com.adbhelper.adb.exceptions;

public class AdbError extends Error {

	public AdbError() {
		// TODO Auto-generated constructor stub
	}

	public AdbError(String message) {
		super(message);
		// TODO Auto-generated constructor stub
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
