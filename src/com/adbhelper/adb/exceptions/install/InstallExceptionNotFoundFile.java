package com.adbhelper.adb.exceptions.install;


public class InstallExceptionNotFoundFile extends InstallException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4952704841184454094L;

	public InstallExceptionNotFoundFile(String labelError) {
		super(labelError);

	}

	public InstallExceptionNotFoundFile(String message, String labelError) {
		super(message, labelError);
	}
	

}
