package ru.ucoz.megadiablo.android.apm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import ru.ucoz.megadiablo.android.apm.ui.EnumPLAF;

/**
 * @author Alexander Gromyko
 * */
public final class Settings {

	private static Settings mSettings;

	private Settings() {
	}

	public static Settings getInstance() {
		if (mSettings == null) {
			init();
		}

		return mSettings;
	}

	private boolean mVisibleSystemPackages = false;
	private int mTimeAutoRefreshDevices = 5000;
	private int mConnectDeviceMaxCount = 5;

	// CONNECT_DEVICE_MAX_COUNT
	private EnumPLAF mLookAndFeel;

	private final Properties mProperties = new Properties();

	private static void init() {
		mSettings = new Settings();
		mSettings.load();

		mSettings.mTimeAutoRefreshDevices = parsePropToInt(
				Consts.settings.DEVICE_AUTO_REFRESH, 5000);
		mSettings.mConnectDeviceMaxCount = parsePropToInt(
				Consts.settings.CONNECT_DEVICE_MAX_COUNT, 5);
		mSettings.mVisibleSystemPackages = parsePropToBoolean(
				Consts.settings.SYSTEM_PACKAGES_VISIBLE, false);

		initLookAndFeel(mSettings.getLookAndFeel());
	}

	public boolean isVisibleSystemPackages() {
		return mVisibleSystemPackages;
	}

	public void setVisibleSystemPackages(final boolean pVisible) {
		mVisibleSystemPackages = pVisible;
		mProperties.setProperty(Consts.settings.SYSTEM_PACKAGES_VISIBLE,
				String.valueOf(pVisible));
	}

	public String getLookAndFeel() {
		return mProperties.getProperty(Consts.settings.LOOK_AND_FEEL, null);
	}

	public void setLookAndFeel(String pLookAndFeel) {
		mProperties.setProperty(Consts.settings.LOOK_AND_FEEL, pLookAndFeel);
	}

	public String getAdbPath() {
		return mProperties.getProperty(Consts.settings.PATH_ADB, "adb");
	}

	public void setAdbPath(String pAdbPath) {
		mProperties.setProperty(Consts.settings.PATH_ADB, pAdbPath);
	}

	public String getPackageFilterName() {
		return mProperties.getProperty(Consts.settings.FILTER_TEXT, "");
	}

	public void setPackageFilterName(String pPackageFilterName) {
		mProperties
				.setProperty(Consts.settings.FILTER_TEXT, pPackageFilterName);
	}

	public int getTimeAutoRefreshDevices() {
		return mTimeAutoRefreshDevices;
	}

	public void setTimeAutoRefreshDevices(int pTimeAutoRefreshDevices) {
		mTimeAutoRefreshDevices = pTimeAutoRefreshDevices;
		mProperties.setProperty(Consts.settings.DEVICE_AUTO_REFRESH,
				String.valueOf(pTimeAutoRefreshDevices));
	}

	public int getConnectDeviceMaxCount() {
		return mConnectDeviceMaxCount;
	}

	public void setConnectDeviceMaxCount(int pConnectDeviceMaxCount) {
		mConnectDeviceMaxCount = pConnectDeviceMaxCount;
		mProperties.setProperty(Consts.settings.CONNECT_DEVICE_MAX_COUNT,
				String.valueOf(pConnectDeviceMaxCount));
	}

	public String getConnectDeviceByNumber(int pNum) {
		String key = Consts.settings.CONNECT_DEVICE_NUMBER
				+ String.valueOf(pNum);
		return mProperties.getProperty(key, "");
	}

	public void setConnectDeviceByNumber(int pNum, String pConnect) {
		String key = Consts.settings.CONNECT_DEVICE_NUMBER
				+ String.valueOf(pNum);
		mProperties.setProperty(key, pConnect);
	}

	public EnumPLAF getPLookAndFeel() {
		if (mLookAndFeel == null) {
			mLookAndFeel = EnumPLAF.AERO;
		}
		return mLookAndFeel;
	}

	public void load() {
		try {
			mProperties.load(new FileInputStream(Consts.settings.FILE_PROP));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			mProperties.store(new FileOutputStream(Consts.settings.FILE_PROP),
					"");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int parsePropToInt(String pProp, int pDef) {
		return parseStringToInt(mSettings.mProperties.getProperty(pProp), pDef);
	}

	private static boolean parsePropToBoolean(String pProp, boolean pDef) {
		return parseStringToBoolean(mSettings.mProperties.getProperty(pProp),
				pDef);
	}

	private static int parseStringToInt(String pValue, int pDef) {
		if (pValue == null) {
			return pDef;
		}
		if (pValue.matches("[0-9]+")) {
			try {
				return Integer.parseInt(pValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pDef;
	}

	private static boolean parseStringToBoolean(String pValue, boolean pDef) {
		if (pValue == null) {
			return pDef;
		}
		try {
			return Boolean.parseBoolean(pValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pDef;
	}

	private static void initLookAndFeel(final String pName) {
		mSettings.mLookAndFeel = null;

		if (pName != null) {
			mSettings.mLookAndFeel = EnumPLAF.findByName(pName);
		}

		if (mSettings.mLookAndFeel == null) {
			mSettings.mLookAndFeel = EnumPLAF.AERO;
		}
	}

}
