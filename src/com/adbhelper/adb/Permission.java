/**
 *
 */
package com.adbhelper.adb;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class Permission {
	private String name;

	public Permission(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
