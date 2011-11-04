/**
 * 
 */
package com.adbhelper.adb.exseptions;

import com.adbhelper.adb.AdbConsts;

/**
 * @author Uladzimir Baraznouski
 *
 */
public class NotAccessPackageManager extends AdbException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2860916151188736961L;

	/**
     * 
     */
    public NotAccessPackageManager() {
	super(AdbConsts.STR_ERROR_NOT_ACCESS_PACKAGE_MANAGER);
    }

    
}
