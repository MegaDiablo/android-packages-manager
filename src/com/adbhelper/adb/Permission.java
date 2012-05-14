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
	private String label;

	public Permission(AdbModule adb, String name) {
		super();
		this.name = name;
		this.label = adb.getLabelPermisssion(name);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		if (label != null) {
			return label;
		}
		return name;
	}

}
