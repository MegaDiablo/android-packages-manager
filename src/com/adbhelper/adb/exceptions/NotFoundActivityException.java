/**
 * 
 */
package com.adbhelper.adb.exceptions;

/**
 * @author Uladzimir Baraznouski
 *
 */
public class NotFoundActivityException extends AdbException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5723212553740385624L;

	/**
     * 
     */
    public NotFoundActivityException() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public NotFoundActivityException(String message) {
	super(message);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public NotFoundActivityException(Throwable cause) {
	super(cause);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public NotFoundActivityException(String message, Throwable cause) {
	super(message, cause);
	// TODO Auto-generated constructor stub
    }

}
