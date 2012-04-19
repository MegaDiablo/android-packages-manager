package com.adbhelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.adbhelper.adb.AdbDevice;
import com.adbhelper.adb.AdbModule;
import com.adbhelper.adb.AdbPackage;
import com.adbhelper.adb.Permission;
import com.adbhelper.adb.exceptions.NotAccessPackageManager;
import com.adbhelper.adb.exceptions.install.InstallException;

public class TestCommand {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File propfile=new File("aa.prop");
		AdbModule adb;
		try {
			adb = new AdbModule(
					"/home/vbaraznovsky/android/android-sdk-linux/platform-tools/adb",
					propfile.getAbsolutePath());

			// AdbModule adb=new AdbModule("adb");
			// adb.devices().get(0).getPackagesNonSystem().get(18).monkey(5000);
			try {
				List<AdbPackage> packages = adb.devices().get(0)
						.getPackagesNonSystem();
				int index = packages.indexOf(new AdbPackage("com.ximad.snake"));
				if (index != -1) {
					System.out.println(packages.get(index));
				}
			} catch (NotAccessPackageManager e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			adb.devices().get(0).clearTemp();
			List<AdbDevice> devices = adb.devices();
			for (AdbDevice adbDevice : devices) {
				Map<String, String> prop = adb.getPropertiesDevice(adbDevice);
				System.out.print("!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.print(prop.get("ro.product.manufacturer"));
				System.out.print(" ");
				System.out.println(prop.get("ro.product.model"));
				System.out.println(adbDevice.getLabel());


			}


			// adb.runAapt("dump badging /media/Work/workspaceAndroid/!Projects/update/BrainCube/CR_1_5_Label_2011_12_20_liteHD/BrainCubeLiteHD1.5.apk".split(" "));
			AdbPackage info = adb
					.getInfoApk("/media/Work/workspaceAndroid/!Projects/update/BrainCube/CR_1_5_Label_2011_12_20_liteHD/BrainCubeLiteHD1.5.apk");
			System.out.println(info);
			System.out.println(info.getVersionCode());
			System.out.println(info.getVersionName());
			for (Permission permission : info.getPermissions()) {
				System.out.println(permission);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		propfile.deleteOnExit();
	}
	/*
	 * [29.12.2011 12:24:36] : badging Print the label and icon for the app
	 * declared in APK. [29.12.2011 12:24:36] : permissions Print the
	 * permissions from the APK. [29.12.2011 12:24:36] : resources Print the
	 * resource table from the APK. [29.12.2011 12:24:36] : configurations Print
	 * the configurations in the APK. [29.12.2011 12:24:36] : xmltree Print the
	 * compiled xmls in the given assets. [29.12.2011 12:24:36] : xmlstrings
	 * Print the strings of the given compiled xml assets.
	 */
}
