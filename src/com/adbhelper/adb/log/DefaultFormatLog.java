package com.adbhelper.adb.log;


public class DefaultFormatLog implements FormatLog {

    private static final String STR_FAIL = "Failure";
    private final LogAdb logAdb;

    public DefaultFormatLog(LogAdb logAdb) {
		super();
		this.logAdb = logAdb;
	}

	public String changeLine(String line) {
	if (line.startsWith(STR_FAIL))
	{
	    error(line);
	    return null;
	}
	return line;
    }

	@Override
	public void error(String line) {
		logAdb.error(line);
	}

	@Override
	public void debug(String line) {
		logAdb.debug(line);
	}

	@Override
	public void info(String line) {
		logAdb.info(line);
	}



}
