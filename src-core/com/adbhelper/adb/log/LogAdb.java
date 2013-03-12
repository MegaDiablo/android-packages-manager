package com.adbhelper.adb.log;

import java.util.Date;

import com.adbhelper.adb.ILogListener;

public class LogAdb {

	private static boolean debugMode = false;
	private ILogListener logListener = null;

	@SuppressWarnings("deprecation")
	public void debug(final String log) {
		if (!debugMode) {
			return;
		}
		if (log == null)
			return;

		long currentTime = System.currentTimeMillis();
		System.out.print("[" + new Date(currentTime).toLocaleString()
				+ "] : (--Debug--) ");
		// System.out.print(new Time(System.currentTimeMillis())+" : ");
		System.out.print(log);
		System.out.println();
		if (logListener != null) {
			logListener.onDebug(currentTime, log);
		}

	}

	public void info(final String log, final Object... args) {
		info(String.format(log, args));
	}

	@SuppressWarnings("deprecation")
	public void info(final String log) {
		if (log == null)
			return;
		long currentTime = System.currentTimeMillis();
		printInfo("[" + new Date(currentTime).toLocaleString() + "] : ", false);
		// System.out.print(new Time(System.currentTimeMillis())+" : ");
		printInfo(log, true);
		if (logListener != null) {
			logListener.onInfo(currentTime, log);
		}

	}

	public void printInfo(final String log, final Object... args) {
		printInfo(String.format(log, args), true);
		if (logListener != null) {
			logListener.onInfo(-1, log);
		}
	}

	private void printInfo(final String log, final boolean newLine) {
		if (log == null)
			return;
		System.out.print(log);
		if (newLine) {
			System.out.println();
		}
	}

	@SuppressWarnings("deprecation")
	public void error(final String log) {
		if (log == null)
			return;
		long currentTime = System.currentTimeMillis();
		System.out.print("[" + new Date(currentTime).toLocaleString() + "] : ");
		System.out.flush();

		// System.out.print(new Time(System.currentTimeMillis())+" : ");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println(log);

		System.err.flush();
		if (logListener != null) {
			logListener.onError(currentTime, log);
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public ILogListener getLogListener() {
		return logListener;
	}

	public void setLogListener(final ILogListener pLogListener) {
		logListener = pLogListener;
	}

	public static boolean isDebugMode() {
		return debugMode;
	}

	public static void setDebugMode(final boolean pDebugMode) {
		debugMode = pDebugMode;
	}



}
