package com.adbhelper.adb.exceptions.install;

import com.adbhelper.adb.AdbConsts;

public class InstallExceptionAlreadyExists extends InstallException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4952704841184454094L;

	public InstallExceptionAlreadyExists(String labelError) {
		super(AdbConsts.STR_ERROR_MESSAGE_INSTALL_ALREADY_EXITS,labelError);

	}

	public InstallExceptionAlreadyExists(String message, String labelError) {
		super(message, labelError);
	}
	

}
