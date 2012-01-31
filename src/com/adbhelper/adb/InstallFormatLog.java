package com.adbhelper.adb;

public class InstallFormatLog extends DefaultFormatLog {
    
    
    private static final String BEGIN_COMPLITE_UPLOAD = "\tpkg: ";
    private static final String LOG_UPLOAD_COMPLITE = "Upload complite";

    @Override
    public String changeLine(String line) {
	if (line.startsWith(BEGIN_COMPLITE_UPLOAD))
	{
	    LogAdb.info(LOG_UPLOAD_COMPLITE);
	    return "Installing "+line.substring(1)+" ...";
	}
	return super.changeLine(line);
    }

}
