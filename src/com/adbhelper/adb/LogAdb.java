package com.adbhelper.adb;

import java.util.Date;

public class LogAdb {

	private static boolean debugMode = false;

	@SuppressWarnings("deprecation")
	public static void debug(String log) {
		if (!debugMode) {
			return;
		}
		if (log == null)
			return;
		System.out.print("["
				+ new Date(System.currentTimeMillis()).toLocaleString()
				+ "] : (--Debug--) ");
		// System.out.print(new Time(System.currentTimeMillis())+" : ");
		System.out.print(log);
		System.out.println();
	}

	public static void info(String log,Object...args) {
		info(String.format(log, args));
	}
	@SuppressWarnings("deprecation")
	public static void info(String log) {
		if (log == null)
			return;
		System.out.print("["
				+ new Date(System.currentTimeMillis()).toLocaleString()
				+ "] : ");
		// System.out.print(new Time(System.currentTimeMillis())+" : ");
		System.out.println(log);
	}

	@SuppressWarnings("deprecation")
	public static void error(String log) {
		if (log == null)
			return;
		System.out.print("["
				+ new Date(System.currentTimeMillis()).toLocaleString()
				+ "] : ");
		System.out.flush();

		// System.out.print(new Time(System.currentTimeMillis())+" : ");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println(log);

		System.err.flush();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
