package ru.ucoz.megadiablo.android.apm.ui.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ru.ucoz.megadiablo.android.apm.Consts;
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

	private List<SettingsChangedListener> mSettingsChangedListeners;

	private boolean mVisibleSystemPackages = false;
	private boolean mAutostartPackage = false;
	private boolean mAutorefreshListDevices = false;
	private boolean mUseReinstall = false;
	private int mTimeAutoRefreshDevices = Consts.Default.AUTO_REFRESH_DEVICES;
	private int mConnectDeviceMaxCount =
			Consts.Default.CONNECT_DEVICE_MAX_COUNT;
	private int mMonkeyCount = Consts.Default.MONKEY_COUNT;
	// CONNECT_DEVICE_MAX_COUNT
	private EnumPLAF mLookAndFeel;

	private final Properties mProperties = new Properties();



	private static void init() {
		mSettings = new Settings();
		mSettings.load();

		mSettings.mSettingsChangedListeners =
				new ArrayList<SettingsChangedListener>();

		mSettings.mTimeAutoRefreshDevices =
				parsePropToInt(
						Consts.Settings.DEVICE_AUTO_REFRESH_TIME,
						Consts.Default.AUTO_REFRESH_DEVICES);
		mSettings.mMonkeyCount =
				parsePropToInt(
						Consts.Settings.MONKEY_COUNT,
						Consts.Default.MONKEY_COUNT);

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

		mSettings.mAutorefreshListDevices =
				parsePropToBoolean(
						Consts.Settings.SETTINGS_DEVICES_AUTOREFRESH,
						false);

		mSettings.mTimeAutoRefreshDevices =
				parsePropToInt(
						Consts.Settings.DEVICE_AUTO_REFRESH_TIME,
						Consts.Default.AUTO_REFRESH_DEVICES);

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

		fireChangedListener(Consts.Settings.SYSTEM_PACKAGES_VISIBLE);
	}

	public boolean isAutostartPackage() {
		return mAutostartPackage;
	}

	public void setAutostartPackage(final boolean pVisible) {
		mAutostartPackage = pVisible;
		mProperties.setProperty(
				Consts.Settings.SETTINGS_PACKAGE_AUTOSTART,
				String.valueOf(pVisible));

		fireChangedListener(Consts.Settings.SETTINGS_PACKAGE_AUTOSTART);
	}

	public boolean isAutorefreshListDevices() {
		return mAutorefreshListDevices;
	}

	public void setAutorefreshListDevices(final boolean pVisible) {
		mAutorefreshListDevices = pVisible;
		mProperties.setProperty(
				Consts.Settings.SETTINGS_DEVICES_AUTOREFRESH,
				String.valueOf(pVisible));

		fireChangedListener(Consts.Settings.SETTINGS_DEVICES_AUTOREFRESH);
	}

	public boolean isUseReinstall() {
		return mUseReinstall;
	}

	public void setUseReinstall(final boolean pVisible) {
		mUseReinstall = pVisible;
		mProperties.setProperty(
				Consts.Settings.SETTINGS_PACKAGE_USE_REINSTALL,
				String.valueOf(pVisible));

		fireChangedListener(Consts.Settings.SETTINGS_PACKAGE_USE_REINSTALL);
	}

	public String getLookAndFeel() {
		return mProperties.getProperty(Consts.Settings.LOOK_AND_FEEL, null);
	}

	public void setLookAndFeel(final String pLookAndFeel) {
		mProperties.setProperty(Consts.Settings.LOOK_AND_FEEL, pLookAndFeel);

		fireChangedListener(Consts.Settings.LOOK_AND_FEEL);
	}

	public String getAdbPath() {
		return mProperties.getProperty(Consts.Settings.PATH_ADB, "adb");
	}

	public void setAdbPath(final String pAdbPath) {
		mProperties.setProperty(Consts.Settings.PATH_ADB, pAdbPath);

		fireChangedListener(Consts.Settings.PATH_ADB);
	}

	public String getAAPTPath() {
		return mProperties.getProperty(Consts.Settings.PATH_AAPT, "aapt");
	}

	public void setAAPTPath(final String pAAPTPath) {
		mProperties.setProperty(Consts.Settings.PATH_AAPT, pAAPTPath);

		fireChangedListener(Consts.Settings.PATH_AAPT);
	}

	public String getPackageFilterName() {
		return mProperties.getProperty(Consts.Settings.FILTER_TEXT, "");
	}

	public void setPackageFilterName(final String pPackageFilterName) {
		mProperties
				.setProperty(Consts.Settings.FILTER_TEXT, pPackageFilterName);

		fireChangedListener(Consts.Settings.FILTER_TEXT);
	}

	public int getTimeAutoRefreshDevices() {
		return mTimeAutoRefreshDevices;
	}

	public void setTimeAutoRefreshDevices(final int pTimeAutoRefreshDevices) {
		mTimeAutoRefreshDevices = pTimeAutoRefreshDevices;
		mProperties.setProperty(
				Consts.Settings.DEVICE_AUTO_REFRESH_TIME,
				String.valueOf(pTimeAutoRefreshDevices));

		fireChangedListener(Consts.Settings.DEVICE_AUTO_REFRESH_TIME);
	}

	public int getConnectDeviceMaxCount() {
		return mConnectDeviceMaxCount;
	}

	public void setConnectDeviceMaxCount(final int pConnectDeviceMaxCount) {
		mConnectDeviceMaxCount = pConnectDeviceMaxCount;
		mProperties.setProperty(
				Consts.Settings.CONNECT_DEVICE_MAX_COUNT,
				String.valueOf(pConnectDeviceMaxCount));

		fireChangedListener(Consts.Settings.CONNECT_DEVICE_MAX_COUNT);
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

		fireChangedListener(Consts.Settings.CONNECT_DEVICE_NUMBER);
	}

	public EnumPLAF getPLookAndFeel() {
		if (mLookAndFeel == null) {
			mLookAndFeel = EnumPLAF.AERO;
		}
		return mLookAndFeel;
	}

	public int getMonkeyCount() {
		return mMonkeyCount;
	}

	public void setMonkeyCount(final int pMonkeyCount) {
		mMonkeyCount = pMonkeyCount;
				mProperties.setProperty(
				Consts.Settings.MONKEY_COUNT,
				String.valueOf(pMonkeyCount));
		fireChangedListener(Consts.Settings.MONKEY_COUNT);
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

	public void fireChangedListener(final String pName) {
		for (int i = 0; i < mSettingsChangedListeners.size(); i++) {
			SettingsChangedListener item = mSettingsChangedListeners.get(i);
			item.changedSettings(pName);
		}
	}

	public void addChangedListener(final SettingsChangedListener pListener) {
		if (!mSettingsChangedListeners.contains(pListener)) {
			mSettingsChangedListeners.add(pListener);
		}
	}

	public void removeChangedListener(final SettingsChangedListener pListener) {
		mSettingsChangedListeners.remove(pListener);
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
