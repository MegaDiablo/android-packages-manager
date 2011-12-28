/**
 * 
 */
package com.adbhelper.adb.exceptions;

/**
 * @author Uladzimir Baraznouski
 *
 */
public class DeviceIsEmulatorRebootException extends AdbException {

   

    /**
	 * 
	 */
	private static final long serialVersionUID = 2404364128174181190L;

	/**
     * @param message
     */
    public DeviceIsEmulatorRebootException() {
	super("Reboot denied: Device is emulator");
	// TODO Auto-generated constructor stub
    }
 
}
