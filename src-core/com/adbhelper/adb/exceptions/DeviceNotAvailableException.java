package com.adbhelper.adb.exceptions;
/**
 *
 * @author Vladimir Baraznovsky
 *
 */
public class DeviceNotAvailableException extends AdbException {

	private static final long serialVersionUID = -7157639125181452841L;

	public DeviceNotAvailableException() {
		super("Device not Available");
	}

}
