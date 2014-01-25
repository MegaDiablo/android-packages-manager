package ru.ucoz.megadiablo.android.apm;

/**
 * @author MegaDiablo
 * */
public final class Consts {

	private Consts() {
	}

	public static final String VERSION_APP = "%%VERSION%%";
	public static final String YEAR_APP = "%%YEAR%%";

	/**
	 * Класс с константами настроек.
	 * */
	public final class Settings {
		public static final String PATH_ADB = "path.adb";
		public static final String PATH_AAPT = "path.aapt";
		public static final String FILTER_TEXT = "filter.text";
		public static final String DEVICE_AUTO_REFRESH_TIME =
				"device.auto.refresh";
		public static final String MONKEY_COUNT =
				"monkey.count";

		public static final String CONNECT_DEVICE_COUNT =
				"connect.device.count";
		public static final String CONNECT_DEVICE_NUMBER = "connect.device.";
		public static final String CONNECT_DEVICE_MAX_COUNT =
				"connect.device.max.count";

		public static final String LOOK_AND_FEEL = "plaf";
		public static final String SETTINGS_THEMES_PATH = "settings.themes.path";

		public static final String SYSTEM_PACKAGES_VISIBLE =
				"system.packages.visible";

		public static final String SETTINGS_PACKAGE_AUTOSTART =
				"settings.package.autostart";
		public static final String SETTINGS_PACKAGE_USE_REINSTALL =
				"settings.package.use.reinstall";

		public static final String SETTINGS_DEVICES_AUTOREFRESH =
				"settings.devices.autorefresh";
		
		public static final String SETTINGS_ADB_CONSOLE_CHARSET =
				"settings.adb.console.charset";

		public static final String FILE_PROP = "properties/apm.prop";
		public static final String FILE_PROP_APP = "properties/app.prop";
		public static final String FILE_PROP_FILTER = "properties/filter.prop";

		private Settings() {
		}
	}

	/**
	 * Класс со значениями по умолчанию.
	 * */
	public final class Default {

		public static final int DELAY_EVENT_UPDATER = 5000;
		public static final int AUTO_REFRESH_DEVICES = 5000;
		public static final int MONKEY_COUNT = 1000;
		public static final int CONNECT_DEVICE_MAX_COUNT = 5;

		private Default() {
		}
	}
}
