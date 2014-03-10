/**
 *
 */
package com.adbhelper.adb;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.adbhelper.adb.exceptions.DeviceNotAvailableException;
import com.adbhelper.adb.exceptions.NotFoundActivityException;
import com.adbhelper.adb.exceptions.install.InstallException;
import com.adbhelper.adb.shell.AdbShell;

/**
 * @author Uladzimir Baraznouski
 *
 */
public class AdbPackage {

	private String name;
	private String fileName;
	private String device;
	private String defaultActivity;
	private String label;
	private AdbModule adb;
	private String versionCode;
	private String versionName;
	private List<Permission> permissions;

	public AdbPackage(final String namePackage) {
	this(null, namePackage,null, null);


	}

	public AdbPackage(final AdbModule adb, final String namePackage, final String file,
			final String device) {
		this.name = namePackage;
		this.device = device;
		this.fileName = file;
		this.adb = adb;
		if (adb != null) {
			this.defaultActivity = adb.getNameActivity(namePackage);
			this.label = adb.getLabelActivity(namePackage);
		}


	}

	public String getName() {
		return name;
	}

	public String getNameDevice() {
		return device;
	}

	public void setTempDefaultActivity(final String defaultActivity) {
		this.defaultActivity = defaultActivity;

	}

	public void setDefaultActivity(final String defaultActivity) throws IOException {
		setTempDefaultActivity(defaultActivity);
		if (defaultActivity != null) {
			adb.setNameActivity(name, defaultActivity);
		}

	}

	public String getDefaultActivity() {
		return defaultActivity;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) throws IOException {
		this.label = label;
		if (this.label != null) {
			adb.setLabelActivity(name, this.label);
		}

	}

	public String getDevice() {
		return device;
	}

	public void setDevice(final String device) {
		this.device = device;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return adb.formatInfoPackage(this);
	}

	public void uninstall() throws DeviceNotAvailableException {
		adb.uninstall(device, name);
	}
	public void clearData() throws DeviceNotAvailableException {
		adb.clearData(device, name);
	}

	@Deprecated
	public void reinstall(final String activity, final String pathApp)
			throws InstallException, DeviceNotAvailableException {
		adb.reinstall(device, name, activity, pathApp);
	}

	public void reinstall(final String pathApp) throws InstallException, DeviceNotAvailableException {
		adb.reinstall(device, pathApp);
	}

	public void start(final String activity) throws DeviceNotAvailableException {
		adb.startActivity(device, name, activity);
	}

	public void start() throws NotFoundActivityException, DeviceNotAvailableException {
		if (defaultActivity == null) {
			throw new NotFoundActivityException();
		}
		adb.startActivity(device, name, defaultActivity);
	}

	public void debug(final String activity) throws DeviceNotAvailableException {
		adb.debugActivity(device, name, activity);
	}

	public void debug() throws NotFoundActivityException, DeviceNotAvailableException {
		if (defaultActivity == null) {
			throw new NotFoundActivityException();
		}
		adb.debugActivity(device, name, defaultActivity);
	}

	public void download(final String toPath) throws DeviceNotAvailableException {
		adb.downloadFile(device, fileName, toPath);
	}

	public void download() throws DeviceNotAvailableException {
		download(null);

	}

	/**
	 * start process "Monkey"
	 *
	 * @param count
	 *            - count events
	 * @throws DeviceNotAvailableException
	 */
	public void monkey(final int count) throws DeviceNotAvailableException {
		adb.monkey(device, name, count);
	}

	public void setPerrmissons(final List<String> permissions) {
		this.permissions = new LinkedList<Permission>();
		for (String string : permissions) {
			this.permissions.add(new Permission(adb,string));
		}
	}

	public void setListPerrmissons(final List<Permission> permissions) {
		this.permissions = permissions;

	}


	public void setVersionName(final String versionName) {
		this.versionName = versionName;

	}

	public void setVersionCode(final String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void update() throws DeviceNotAvailableException{
		adb.updatePackage(this);
	}

	public void updateInfo(final AdbPackage adbPackage) throws IOException
	{
		this.setDefaultActivity(adbPackage.getDefaultActivity());
		this.setLabel(adbPackage.getLabel());
		this.setVersionCode(adbPackage.getVersionCode());
		this.setVersionName(adbPackage.getVersionName());
		this.setListPerrmissons(adbPackage.getPermissions());

	}

	public AdbShell createShellRunAs(final String... cmd) {
		return adb.createShellRunAs(device, name, cmd);
	}

	public AdbShell createShellRunAs(final String cmd) {
		return adb.createShellRunAs(device, name, cmd);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (name == null) {
			return false;
		}
		if (obj instanceof AdbPackage) {
			AdbPackage oPackage = (AdbPackage) obj;
			return name.equals(oPackage.name);
		}
		if (obj instanceof String) {
			obj.toString().equals(name);
		}

		return false;
	}




}
