package com.adbhelper.adb;

import java.util.List;
import java.util.Map;

import com.adbhelper.adb.exceptions.DeviceIsEmulatorRebootException;
import com.adbhelper.adb.exceptions.NotAccessPackageManager;
import com.adbhelper.adb.exceptions.install.InstallException;
import com.adbhelper.adb.shell.AdbShell;

public class AdbDevice {

	private static final String EMULATOR_NAME = "emulator\\-[0-9]*";
	private static final boolean DEFAULT_OPTIONS_WITH_SYSTEM = false;
	private static final String PROPERTY_MANUFACTURER = "ro.product.manufacturer";
	private static final String PROPERTY_MODEL = "ro.product.model";
	private static final String PROPERTY_VERSION_API = "ro.build.version.sdk";
	private static final String PROPERTY_VERSION_NAME = "ro.build.version.release";
	private String name;
	private String type;
	private AdbModule adb;
	private List<AdbPackage> listPackges;
	private Map<String, String> properties;

	public AdbDevice(final String name, final String type, final AdbModule adb) {
		super();
		this.setName(name);
		this.setType(type);
		this.adb = adb;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	public int uninstall(final String app) {
		return adb.uninstall(name, app);
	}

	public int clearData(final String app) {
		return adb.clearData(name, app);
	}


	@Deprecated
	public String install(final String pathApp) throws InstallException {
		return adb.install(name, pathApp);
	}

	public String install(final String pathApp, final boolean autostart)
			throws InstallException {
		return adb.install(name, pathApp, autostart);
	}

	public void reinstall(final String pathApp) throws InstallException {
		adb.reinstall(name, pathApp);
	}

	public void reinstall(final String pathApp, final boolean autoStart)
			throws InstallException {
		adb.reinstall(name, pathApp, autoStart);
	}

	@Deprecated
	public void reinstall(final String app, final String activity, final String pathApp)
			throws InstallException {
		adb.reinstall(name, app, activity, pathApp);
	}

	public void start(final String app, final String activity) {
		adb.startActivity(name, app, activity);
	}

	public void reboot() throws DeviceIsEmulatorRebootException {
		if (isEmulator()) {
			throw new DeviceIsEmulatorRebootException();
		}
		adb.reboot(name);
	}

	public List<AdbPackage> refreshListPackages()
			throws NotAccessPackageManager {
		return refreshListPackages(false);
	}

	public List<AdbPackage> refreshListPackages(final boolean withSystem)
			throws NotAccessPackageManager {
		listPackges = adb.getPackages(name, withSystem);
		return listPackges;
	}

	public List<AdbPackage> getPackagesNonSystem(final boolean refreshList)
			throws NotAccessPackageManager {
		List<AdbPackage> packges = adb.getPackagesNonSystem(name);
		if (refreshList) {
			listPackges = packges;
		}
		return packges;
	}

	public List<AdbPackage> getPackagesNonSystem()
			throws NotAccessPackageManager {
		return getPackagesNonSystem(false);
	}

	public List<AdbPackage> getPackages() throws NotAccessPackageManager {
		return getPackages(DEFAULT_OPTIONS_WITH_SYSTEM);
	}

	public List<AdbPackage> getPackages(final boolean withSystem)
			throws NotAccessPackageManager {
		if (listPackges == null) {
			refreshListPackages(withSystem);
		}
		return listPackges;
	}

	@Override
	public String toString() {
	//	return name;
		return getLabel();
	}

	public boolean isEmulator() {
		return name.matches(EMULATOR_NAME);
	}

	public void sendKeyCode(final int keyCode) {
		adb.sendKeyCode(this, keyCode);
	}

	public void sendTap(final int x, final int y) {
		adb.sendTap(this, x, y);
	}

	public void sendSwipe(final int fromX, final int fromY,final int toX, final int toY) {
		adb.sendSwipe(this, fromX, fromY, toX, toY);
	}

	public void takeScreenshot(String path) {
		adb.takeScreenshot(this, path);
	}

	public void clearTemp() {
		adb.clearTemp(this);
	}

	public Map<String, String> getProperties() {
		if (properties == null) {
			properties = adb.getPropertiesDevice(this);
		}
		return properties;
	}

	public String getProperty(final String key) {
		return getProperties().get(key);
	}

	public String getManufacturer() {
		return getProperty(PROPERTY_MANUFACTURER);

	}

	public String getModel() {
		return getProperty(PROPERTY_MODEL);

	}

	public String getVersionApi() {
		return getProperty(PROPERTY_VERSION_API);

	}

	public String getVersionName() {
		return getProperty(PROPERTY_VERSION_NAME);

	}

	public String getLabel() {
		String manufacturer = getManufacturer();
		String model = getModel();
		String version = getVersionName();
		if (isEmulator()){
			manufacturer=name;
		}
		String result = "";
		boolean hasInfo = false;

		if (manufacturer != null) {
			result += manufacturer;
			hasInfo = true;
		}
		if (model != null) {
			if (hasInfo) {
				result += " ";
			}
			result += model;
			hasInfo = true;
		} else {
			if (!hasInfo) {
				result += name;
				hasInfo = true;
			}
		}
		if (version != null) {
			if (hasInfo) {
				result += " ";
			}
			result += "("+version+")";
			hasInfo = true;
		}
		return result;

	}

	public void updatePackages(final boolean withSystem) throws NotAccessPackageManager{
		adb.updatePackages(getPackages(withSystem));
	}

	public void updatePackages() throws NotAccessPackageManager{
		adb.updatePackages(getPackages(DEFAULT_OPTIONS_WITH_SYSTEM));
	}

	public AdbShell createShell(final String cmd) {
		return adb.createShell(name, cmd);
	}

	public AdbShell createShell(final String... cmd) {
		return adb.createShell(name, cmd);
	}
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AdbDevice)) {
			return false;
		}
		return this.name.equals(((AdbDevice) obj).name);
	}

}
