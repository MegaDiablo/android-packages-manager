package com.adbhelper.adb.log;

public interface FormatLog {
    String changeLine(String line);
	void error(String line);
	void debug(String line);
	void info(String line);
}
