package com.adbhelper;

public enum AdbCommand {
    REINSTALL {
	@Override
	public String getNameArg() {
	    return "reinstall";
	}
    },INSTALL {
	@Override
	public String getNameArg() {
	    return "install";
	}
    },UNINSTALL{

	@Override
	public String getNameArg() {
	    return "uninstall";
	}
	
    };
    public abstract String getNameArg(); 
}
