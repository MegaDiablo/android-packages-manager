package com.adbhelper.adb.exceptions.install;

import java.util.Arrays;

import com.adbhelper.adb.AdbConsts;
import com.adbhelper.adb.exceptions.AdbError;
import com.adbhelper.adb.exceptions.AdbException;

public class InstallException extends AdbException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2203615620788253868L;
	protected String mLabelError;

	public InstallException(String labelError) {
		this(String.format(AdbConsts.STR_ERROR_MESSAGE_INSTALL, labelError),
				labelError);
		mLabelError = labelError;

	}

	public InstallException(String message, String labelError) {
		super(message);
		mLabelError = labelError;
	}

	public String getLabelError() {
		return mLabelError;
	}

	public static InstallException createInstallException(String labelError) {
		InstallException installException;
		if (labelError == null) {
			installException = new InstallException(
					InstallErrorMessages.UNKNOWN);
		}
		else if (labelError
				.matches(InstallErrorMessages.CAN_NOT_FAIND_FILE)) {
			installException = new InstallExceptionNotFoundFile(labelError, InstallErrorMessages.INSTALL_FAILED_NOT_FOUND_FILE);
		}
		else if (labelError
				.equals(InstallErrorMessages.INSTALL_FAILED_ALREADY_EXISTS)) {
			installException = new InstallExceptionAlreadyExists(labelError);
		} else {
			installException = new InstallException(labelError);
		}

		StackTraceElement[] st = installException.getStackTrace();
		installException.setStackTrace(Arrays.copyOfRange(st, 1, st.length));

		return installException;

	}

	public static InstallException createInstallException(AdbError e) {
		InstallException installException=createInstallException(e.getMessage());
		installException.setStackTrace(e.getStackTrace());
		return installException;
	}
}
