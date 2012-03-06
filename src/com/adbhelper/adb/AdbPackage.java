/**
 *
 */
package com.adbhelper.adb;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.adbhelper.adb.exceptions.NotFoundActivityException;
import com.adbhelper.adb.exceptions.install.InstallException;

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


	public AdbPackage(AdbModule adb, String namePackage, String file,
			String device) {
		this.name = namePackage;
		this.device = device;
		this.fileName = file;
		this.adb = adb;
		this.defaultActivity = adb.getNameActivity(namePackage);
		this.label = adb.getLabelActivity(namePackage);

	}

	public String getName() {
		return name;
	}

	public String getNameDevice() {
		return device;
	}

	public void setTempDefaultActivity(String defaultActivity) {
		this.defaultActivity = defaultActivity;

	}

	public void setDefaultActivity(String defaultActivity) throws IOException {
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

	public void setLabel(String label) throws IOException {
		this.label = label;
		if (this.label != null) {
			adb.setLabelActivity(name, this.label);
		}

	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return adb.formatInfoPackage(this);
	}

	public void uninstall() {
		adb.uninstall(device, name);
	}

	@Deprecated
	public void reinstall(String activity, String pathApp)
			throws InstallException {
		adb.reinstall(device, name, activity, pathApp);
	}

	public void reinstall(String pathApp) throws InstallException {
		adb.reinstall(device, pathApp);
	}

	public void start(String activity) {
		adb.startActivity(device, name, activity);
	}

	public void start() throws NotFoundActivityException {
		if (defaultActivity == null) {
			throw new NotFoundActivityException();
		}
		adb.startActivity(device, name, defaultActivity);
	}

	public void download(String toPath) {
		adb.downloadFile(device, fileName, toPath);
	}

	public void download() {
		download(null);

	}

	/**
	 * start process "Monkey"
	 *
	 * @param count
	 *            - count events
	 */
	public void monkey(int count) {
		adb.monkey(device, name, count);
	}

	public void setPerrmissons(List<String> permissions) {
		this.permissions=new LinkedList<Permission>();
		for (String string : permissions) {
			this.permissions.add(new Permission(string));
		}
	}

	public void setVersionName(String versionName) {
		this.versionName=versionName;

	}

	public void setVersionCode(String versionCode) {
		this.versionCode=versionCode;
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



}
