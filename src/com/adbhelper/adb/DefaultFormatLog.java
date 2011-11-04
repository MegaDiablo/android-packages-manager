package com.adbhelper.adb;

public class DefaultFormatLog implements FormatLog {

    private static final String STR_FAIL = "Failure";

    public String changeLine(String line) {
	if (line.startsWith(STR_FAIL))
	{
	    LogAdb.error(line);
	    return null;
	}
	return line;
    }

}
