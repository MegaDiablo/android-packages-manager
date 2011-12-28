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
    private AdbModule adb;

    public AdbPackage(AdbModule adb, String namePackage, String file,
	    String device) {
	this.name = namePackage;
	this.device = device;
	this.fileName = file;
	this.adb = adb;
	this.defaultActivity=adb.getNameActivity(namePackage);

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
	adb.setNameActivity(name, defaultActivity);
	
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

    @Override
    public String toString() {
	return name;
    }

    public void uninstall() {
	adb.uninstall(device, name);
    }

    public void reinstall(String activity, String pathApp) throws InstallException {
	adb.reinstall(device, name, activity, pathApp);
    }

    public void reinstall(String pathApp) throws InstallException {
	adb.reinstall(device, name, defaultActivity, pathApp);
    }

    public void start(String activity) {
	adb.startActivity(device, name, activity);
    }

    
    public void start() throws NotFoundActivityException {
	if (defaultActivity==null)
	{
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
