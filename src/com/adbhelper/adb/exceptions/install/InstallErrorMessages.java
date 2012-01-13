/**
 * 
 */
package com.adbhelper.adb.exceptions.install;

/**
 * @author Uladzimir Baraznouski
 * 
 */
public interface InstallErrorMessages {

	public static final String UNKNOWN = "[INSTALL_FAILED_UNKNOWN]";
	public static final String INSTALL_FAILED_OLDER_SDK = "[INSTALL_FAILED_OLDER_SDK]";
	public static final String INSTALL_FAILED_ALREADY_EXISTS = "[INSTALL_FAILED_ALREADY_EXISTS]";
	public static final String INSTALL_FAILED_NOT_FOUND_FILE = "[INSTALL_FAILED_NOT_FOUND_FILE]";
	public static final String INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = "[INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES]";
	public static final String INSTALL_FAILED_INVALID_INSTALL_LOCATION = "[INSTALL_FAILED_INVALID_INSTALL_LOCATION]";
	
	
	public static final String INSTALL_FAILED_INVALID_APK = "[INSTALL_FAILED_INVALID_APK]";
	public static final String INSTALL_FAILED_INSUFFICIENT_STORAGE = "[INSTALL_FAILED_INSUFFICIENT_STORAGE]";
	public static final String INSTALL_FAILED_MISSING_SHARED_LIBRARY = "[INSTALL_FAILED_MISSING_SHARED_LIBRARY]";
	public static final String INSTALL_FAILED_CONFLICTING_PROVIDER = "[INSTALL_FAILED_CONFLICTING_PROVIDER]";
	
	
	public static final String CAN_NOT_FAIND_FILE = "can't find '.*' to install";

}
