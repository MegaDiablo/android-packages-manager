package com.adbhelper;

import java.io.IOException;

import com.adbhelper.adb.AdbModule;
import com.adbhelper.adb.exceptions.install.InstallException;

public class TestCommand {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		
		AdbModule adb;
		try {
			adb = new AdbModule("/home/vbaraznovsky/android/android-sdk-linux/platform-tools/adb","aa.prop");
	
		//AdbModule adb=new AdbModule("adb");
		adb.devices();
		//adb.runAapt("dump badging /media/Work/workspaceAndroid/!Projects/update/BrainCube/CR_1_5_Label_2011_12_20_liteHD/BrainCubeLiteHD1.5.apk".split(" "));
		//System.out.println(adb.getInfoApk("/media/Work/workspaceAndroid/!Projects/update/BrainCube/CR_1_5_Label_2011_12_20_liteHD/BrainCubeLiteHD1.5.apk"));
			try {
				adb.reinstall(null, "/media/Work/workspaceAndroid/!Projects/update/BrainCube/CR_1_5_Label_2011_12_20_liteHD/BrainCubeLiteHD1.5.apk")	;
			} catch (InstallException e) {
			//e.printStackTrace();
				System.out.print(e.getLabelError());
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/*
 * [29.12.2011 12:24:36] :    badging          Print the label and icon for the app declared in APK.
[29.12.2011 12:24:36] :    permissions      Print the permissions from the APK.
[29.12.2011 12:24:36] :    resources        Print the resource table from the APK.
[29.12.2011 12:24:36] :    configurations   Print the configurations in the APK.
[29.12.2011 12:24:36] :    xmltree          Print the compiled xmls in the given assets.
[29.12.2011 12:24:36] :    xmlstrings       Print the strings of the given compiled xml assets.
 * */
}
