/**
 * 
 */
package com.adbhelper.adb;

import java.io.IOException;

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

	public AdbPackage(AdbModule adb, String namePackage, String file,
			String device) {
		this.name = namePackage;
		this.device = device;
		this.fileName = file;
		this.adb = adb;
		this.defaultActivity = adb.getNameActivity(namePackage);
		this.label=adb.getLabelActivity(namePackage);
		

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
		if(defaultActivity!=null){
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
		if (this.label!=null)
		{
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

}
