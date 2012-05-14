package com.adbhelper;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.adbhelper.adb.AdbDevice;
import com.adbhelper.adb.AdbModule;
import com.adbhelper.adb.AdbPackage;
import com.adbhelper.adb.exceptions.NotAccessPackageManager;
import com.adbhelper.adb.log.LogAdb;

public class TestMain {

    private static final String NAME_ARG_APP = "-app";
    private static final String NAME_ARG_FILE = "-file";
    private static final String NAME_ARG_ACTIVITY = "-activity";

    /**
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {
	//testing git

    // FileSystemView.getFileSystemView().getSystemIcon(f)
	// sun.awt.shell.ShellFolder sf =
	// sun.awt.shell.ShellFolder.getShellFolder(file);



	String fileAdb = "/home/vbaraznovsky/android/android-sdk-linux/platform-tools/adb";
	AdbCommand cmd = null;
	// String fileApk = args[0] + File.separator + "bin" + File.separator+
	// args[1] + ".apk";
	Properties prop = new Properties();
	String app = null;
	String file = null;
	String activity = null;
	String divice = null;
	for (int i = 0; i < args.length; i++) {
	    if (args[i].startsWith(NAME_ARG_APP)) {
		i++;
		app = args[i];
	    } else if (args[i].startsWith(NAME_ARG_FILE)) {
		i++;
		file = args[i];
	    } else if (args[i].startsWith(NAME_ARG_ACTIVITY)) {
		i++;
		activity = args[i];
	    } else {
		boolean isCmd = false;
		for (AdbCommand command : AdbCommand.values()) {
		    if (args[i].startsWith(command.getNameArg())) {
			cmd = command;
			isCmd = true;
			break;
		    }
		}
		if (!isCmd)
		    fileAdb += " " + args[i];

	    }

	}
	AdbModule adb = new AdbModule(fileAdb);
	//adb.connet("192.168.1.29:5555");
//	adb.restart();
System.out.println("endddd");
	//adb.waitDevice();
	final List<AdbDevice> devices = adb.devices();
	if (devices.isEmpty()) {
		System.err.println("Not found devices");
	    return;
	}

	for (AdbDevice adbDevice : devices) {
	    System.out.println(adbDevice.getName() + "="
		    + adbDevice.isEmulator());
	//    adbDevice.sendKeyCode(22);
	}

	Thread thr=new Thread(new Runnable() {

		@Override
		public void run() {
			try {
			    List<AdbPackage> list = devices.get(0).getPackages(false);

			    // list.get(1).setDefaultActivity(".SplashActivity");
			    // list.get(1).start();

			} catch (NotAccessPackageManager e) {
			    e.printStackTrace();
			}
			System.out.println("endThread");
		}
	});
thr.run();
try {
	Thread.sleep(100);
} catch (InterruptedException e) {
	e.printStackTrace();
}

	adb.stopCurrentProcess();
	//LogAdb.debug("end Main");
	//adb.finishAdb();
	// devices.get(0).reboot();
	// adb.uninstall(null, "com.ximad.testtv");
	// adb.uninstall(null, "com.ximad.dropletstv");
	// adb.install(null,
	// "d:\\workspaceEclipse\\ShotBallsTVFragment\\bin\\ShotBallsTVFragment.apk");
	// adb.start(null, "com.ximad.dropletstv",".SplashActivity");
    }

}
