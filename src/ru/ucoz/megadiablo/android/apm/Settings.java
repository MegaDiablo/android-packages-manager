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
	private boolean mAutostartPackage = false;
	private boolean mUseReinstall = false;
	private int mTimeAutoRefreshDevices = Consts.Default.AUTO_REFRESH_DEVICES;
	private int mConnectDeviceMaxCount =
			Consts.Default.CONNECT_DEVICE_MAX_COUNT;

	// CONNECT_DEVICE_MAX_COUNT
	private EnumPLAF mLookAndFeel;

	private final Properties mProperties = new Properties();

	private static void init() {
		mSettings = new Settings();
		mSettings.load();

		mSettings.mTimeAutoRefreshDevices =
				parsePropToInt(
						Consts.Settings.DEVICE_AUTO_REFRESH,
						Consts.Default.AUTO_REFRESH_DEVICES);

		mSettings.mConnectDeviceMaxCount =
				parsePropToInt(
						Consts.Settings.CONNECT_DEVICE_MAX_COUNT,
						Consts.Default.CONNECT_DEVICE_MAX_COUNT);

		mSettings.mVisibleSystemPackages =
				parsePropToBoolean(
						Consts.Settings.SYSTEM_PACKAGES_VISIBLE,
						false);

		mSettings.mAutostartPackage =
				parsePropToBoolean(
						Consts.Settings.SETTINGS_PACKAGE_AUTOSTART,
						false);

		mSettings.mUseReinstall =
				parsePropToBoolean(
						Consts.Settings.SETTINGS_PACKAGE_USE_REINSTALL,
						false);

		initLookAndFeel(mSettings.getLookAndFeel());
	}

	public boolean isVisibleSystemPackages() {
		return mVisibleSystemPackages;
	}

	public void setVisibleSystemPackages(final boolean pVisible) {
		mVisibleSystemPackages = pVisible;
		mProperties.setProperty(
				Consts.Settings.SYSTEM_PACKAGES_VISIBLE,
				String.valueOf(pVisible));
	}

	public boolean isAutostartPackage() {
		return mAutostartPackage;
	}

	public void setAutostartPackage(final boolean pVisible) {
		mAutostartPackage = pVisible;
		mProperties.setProperty(
				Consts.Settings.SETTINGS_PACKAGE_AUTOSTART,
				String.valueOf(pVisible));
	}

	public boolean isUseReinstall() {
		return mUseReinstall;
	}

	public void setUseReinstall(final boolean pVisible) {
		mUseReinstall = pVisible;
		mProperties.setProperty(
				Consts.Settings.SETTINGS_PACKAGE_USE_REINSTALL,
				String.valueOf(pVisible));
	}

	public String getLookAndFeel() {
		return mProperties.getProperty(Consts.Settings.LOOK_AND_FEEL, null);
	}

	public void setLookAndFeel(final String pLookAndFeel) {
		mProperties.setProperty(Consts.Settings.LOOK_AND_FEEL, pLookAndFeel);
	}

	public String getAdbPath() {
		return mProperties.getProperty(Consts.Settings.PATH_ADB, "adb");
	}

	public void setAdbPath(final String pAdbPath) {
		mProperties.setProperty(Consts.Settings.PATH_ADB, pAdbPath);
	}
	
	public String getAAPTPath() {
		return mProperties.getProperty(Consts.Settings.PATH_AAPT, "aapt");
	}
	
	public void setAAPTPath(final String pAAPTPath) {
		mProperties.setProperty(Consts.Settings.PATH_AAPT, pAAPTPath);
	}

	public String getPackageFilterName() {
		return mProperties.getProperty(Consts.Settings.FILTER_TEXT, "");
	}

	public void setPackageFilterName(final String pPackageFilterName) {
		mProperties
				.setProperty(Consts.Settings.FILTER_TEXT, pPackageFilterName);
	}

	public int getTimeAutoRefreshDevices() {
		return mTimeAutoRefreshDevices;
	}

	public void setTimeAutoRefreshDevices(final int pTimeAutoRefreshDevices) {
		mTimeAutoRefreshDevices = pTimeAutoRefreshDevices;
		mProperties.setProperty(
				Consts.Settings.DEVICE_AUTO_REFRESH,
				String.valueOf(pTimeAutoRefreshDevices));
	}

	public int getConnectDeviceMaxCount() {
		return mConnectDeviceMaxCount;
	}

	public void setConnectDeviceMaxCount(final int pConnectDeviceMaxCount) {
		mConnectDeviceMaxCount = pConnectDeviceMaxCount;
		mProperties.setProperty(
				Consts.Settings.CONNECT_DEVICE_MAX_COUNT,
				String.valueOf(pConnectDeviceMaxCount));
	}

	public String getConnectDeviceByNumber(final int pNum) {
		String key =
				Consts.Settings.CONNECT_DEVICE_NUMBER + String.valueOf(pNum);
		return mProperties.getProperty(key, "");
	}

	public void setConnectDeviceByNumber(final int pNum, final String pConnect) {
		String key =
				Consts.Settings.CONNECT_DEVICE_NUMBER + String.valueOf(pNum);
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
			mProperties.load(new FileInputStream(Consts.Settings.FILE_PROP));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		try {
			mProperties.store(
					new FileOutputStream(Consts.Settings.FILE_PROP),
					"");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int parsePropToInt(final String pProp, final int pDef) {
		return parseStringToInt(mSettings.mProperties.getProperty(pProp), pDef);
	}

	private static boolean parsePropToBoolean(final String pProp,
			final boolean pDef) {

		return parseStringToBoolean(
				mSettings.mProperties.getProperty(pProp),
				pDef);
	}

	private static int parseStringToInt(final String pValue, final int pDef) {
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

	private static boolean parseStringToBoolean(final String pValue,
			final boolean pDef) {

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
