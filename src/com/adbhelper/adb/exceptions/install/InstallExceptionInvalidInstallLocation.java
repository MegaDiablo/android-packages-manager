package com.adbhelper.adb.exceptions.install;

import com.adbhelper.adb.AdbConsts;

public class InstallExceptionInvalidInstallLocation extends InstallException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4952704841184454094L;

	public InstallExceptionInvalidInstallLocation(String labelError) {
		super(AdbConsts.STR_ERROR_MESSAGE_INSTALL_FAILED_INVALID_INSTALL_LOCATION,labelError);

	}

	public InstallExceptionInvalidInstallLocation(String message, String labelError) {
		super(message, labelError);
	}
	

}
