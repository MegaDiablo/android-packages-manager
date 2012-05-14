package com.adbhelper.adb.log;

import com.adbhelper.adb.AdbConsts;

public class GetPropertiesDeviceFormatLog extends DefaultFormatLog {




	private static final String STR_ERROR = "Error:";
	public GetPropertiesDeviceFormatLog(LogAdb logAdb) {
		super(logAdb);
	}


	@Override
	public String changeLine(String line) {

		if (line.startsWith(STR_ERROR)) {
			error(line);
			return null;
		}
		if (line.matches(AdbConsts.PATTERN_PROPERTY_DEVICE)) {
			debug(line);
			return null;
		}

		return line;
	}

}
