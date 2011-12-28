package com.adbhelper;

import com.adbhelper.adb.AdbModule;
import com.adbhelper.adb.LogAdb;
import com.adbhelper.adb.exceptions.install.InstallException;

public class Main {

    private static final String NAME_ARG_APP = "-app";
    private static final String NAME_ARG_FILE = "-file";
    private static final String NAME_ARG_ACTIVITY = "-activity";

    /**
     * @param args
     */

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	
	//UIManager.get(null);
	
	//FileSystemView.getFileSystemView().getSystemIcon(f)
	String fileAdb = AdbModule.DEFAULT_PATH_ABD;
	AdbCommand cmd = null;
	// String fileApk = args[0] + File.separator + "bin" + File.separator+
	// args[1] + ".apk";
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
		boolean isCmd=false;
		for (AdbCommand command : AdbCommand.values()) {
		    if (args[i].startsWith(command.getNameArg())) {
			cmd = command;
			isCmd=true;
			break;
		    }
		}
		if(!isCmd)
		    fileAdb+=" "+args[i];
		
	    }
	    
	}
	AdbModule adb = new AdbModule(fileAdb);
	if (adb.devices().isEmpty())
	    {
	    LogAdb.error("Not found devices");
	    return;}
    try {
	switch (cmd) {
	case REINSTALL:
	
			adb.reinstall(divice, app, activity, file);
		break;
	case INSTALL:
	    adb.install(divice, file);
	    break;
	case UNINSTALL:
	    adb.uninstall(divice, app);
	    break;
	default:
	    break;
	}
    } catch (InstallException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
	// adb.uninstall(null, "com.ximad.testtv");
	// adb.uninstall(null, "com.ximad.dropletstv");
	// adb.install(null,
	// "d:\\workspaceEclipse\\ShotBallsTVFragment\\bin\\ShotBallsTVFragment.apk");
	// adb.start(null, "com.ximad.dropletstv",".SplashActivity");
    }
    
}
