package ru.ucoz.megadiablo.android.apm;

/**
 * Перечисление типов событий.
 *
 * @author Alexander Gromyko
 * */
public enum EnumEvents {

	NONE(-1),

	// Группа событий ядра
	SELECT_DEVICE(10000),
	REFRESH_DEVICES(10001),
	REFRESH_PACKAGES(10002),
	REBOOT_DEVICE(10003),
	INSTALL(10004),
	UNINSTALL(10005),
	START_APPLICATION(10006),
	DOWNLOAD(10007),
	SEND_KEY(10008),
	STOP_APPLICATION(10009),
	START_ADB(10010),
	STOP_ADB(10011),
	RESTART_ADB(10012),
	CONNECT_NETWORK_DEVICE(10013),
	CLEAR_FOLDER_TEMP(10014),
	CLEAR_DATA(10015),
	SHELL(10016),
	RUN_AS(10017);
	// STOP_ADB(10011),
	// ;

	private int mType;

	private EnumEvents(final int pType) {
		mType = pType;
	}

	public int getType() {
		return mType;
	}

	public void setType(final int pType) {
		mType = pType;
	}

}
