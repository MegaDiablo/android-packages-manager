package com.adbhelper.adb.exceptions.install;

import com.adbhelper.adb.AdbConsts;

public class InstallExceptionInconsistrentCertificates extends InstallException{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InstallExceptionInconsistrentCertificates() {
		this(InstallErrorMessages.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES);

	}
	public InstallExceptionInconsistrentCertificates(String labelError) {
		super(AdbConsts.STR_ERROR_MESSAGE_INSTALL_INCONSISTENT_CERTIFICATES,labelError);

	}

	public InstallExceptionInconsistrentCertificates(String message, String labelError) {
		super(message, labelError);
	}
	

}
