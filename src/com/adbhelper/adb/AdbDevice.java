package com.adbhelper.adb;

import java.util.List;

import com.adbhelper.adb.exseptions.DeviceIsEmulatorRebootException;
import com.adbhelper.adb.exseptions.NotAccessPackageManager;

public class AdbDevice {

    private static final String EMULATOR_NAME = "emulator\\-[0-9]*";
    private String name;
    private String type;
    private AdbModule adb;
    private List<AdbPackage> listPackges;

    public AdbDevice(String name, String type, AdbModule adb) {
	super();
	this.setName(name);
	this.setType(type);
	this.adb = adb;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
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
    public void setType(String type) {
	this.type = type;
    }

    /**
     * @return the type
     */
    public String getType() {
	return type;
    }

    public int uninstall(String app) {
	return adb.uninstall(name, app);
    }

    public int install(String pathApp) {
	return adb.install(name, pathApp);
    }

    public void reinstall(String app, String activity, String pathApp) {
	adb.reinstall(name, app, activity, pathApp);
    }

    public void start(String app, String activity) {
	adb.startActivity(name, app, activity);
    }

    public void reboot() throws DeviceIsEmulatorRebootException {
	if (isEmulator())
	{
	    throw new DeviceIsEmulatorRebootException();
	}
	adb.reboot(name);
    }

    
    
    public List<AdbPackage> refreshListPackages()
	    throws NotAccessPackageManager {
	return refreshListPackages(false);
    }

    public List<AdbPackage> refreshListPackages(boolean withSystem)
	    throws NotAccessPackageManager {
	listPackges = adb.getPackages(name, withSystem);
	return listPackges;
    }

    public List<AdbPackage> getPackagesNonSystem(boolean refreshList)
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
	return getPackages(false);
    }

    public List<AdbPackage> getPackages(boolean withSystem)
	    throws NotAccessPackageManager {
	if (listPackges == null) {
	    refreshListPackages(withSystem);
	}
	return listPackges;
    }

    @Override
    public String toString() {
	return name;
    }

    public boolean isEmulator(){
	return name.matches(EMULATOR_NAME);
    }
    
    public void sendKeyCode(int keyCode){
	adb.sendKeyCode(this, keyCode);
    }
    
    @Override
    public boolean equals(Object obj) {
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
