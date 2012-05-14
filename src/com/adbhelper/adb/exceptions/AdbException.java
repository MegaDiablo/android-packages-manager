/**
 * 
 */
package com.adbhelper.adb.exceptions;

/**
 * @author Uladzimir Baraznouski
 *
 */
public class AdbException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7650895370046984057L;

	/**
     * 
     */
    public AdbException() {
    }

    /**
     * @param message
     */
    public AdbException(String message) {
	super(message);
    }

    /**
     * @param cause
     */
    public AdbException(Throwable cause) {
	super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public AdbException(String message, Throwable cause) {
	super(message, cause);
    }

    
}


