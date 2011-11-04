/**
 * 
 */
package com.adbhelper.adb;

/**
 * @author Uladzimir Baraznouski
 *
 */
public interface AdbConsts {
    public static final int SUCCESS=0;
    public static final int FAILTURE=1;
    //public static final int ERROR_NOT_ACCESS_PACKAGE_MANAGER = 1;
    
    public static final String STR_ERROR_NOT_ACCESS_PACKAGE_MANAGER = "Error: Could not access the Package Manager.  Is the system running?";

    public static final String STR_SUCCESS = "Success";
    public static final String STR_FAILTURE = "Failure";
    public static final String STR_CONNECT_COMPLITE = "connected to .*";
    
    
    ///--devices    
    //Not found devices
    
    
    ///--install
    //Failure [INSTALL_FAILED_OLDER_SDK]
    
    ///-- Start
    //Activity class {com.ximad.dropletstv/com.ximad.dropletstv.SplashActivity} does not exist.
    
}
