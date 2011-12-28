package com.adbhelper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adbhelper.adb.AdbModule;
import com.adbhelper.adb.exceptions.install.InstallException;

public class TestCommand {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		
		AdbModule adb=new AdbModule("/home/vbaraznovsky/android/android-sdk-linux/platform-tools/adb");
		//AdbModule adb=new AdbModule("adb");
		adb.devices();
			try {
				adb.install(null, "/media/Work/workspaceAndroid/!Projects/update/BrainCube/CR_1_5_Label_2011_12_20_liteHD/BrainCubeLiteHD1.5.apk ")	;
			} catch (InstallException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
				System.out.print(e.getLabelError());
				
			}
	}

}
