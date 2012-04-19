package com.adbhelper.adb.log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.adbhelper.adb.AdbConsts;
import com.adbhelper.adb.AdbModule;
import com.adbhelper.adb.AdbPackage;
import com.adbhelper.adb.AdbUtils;

public class GetPropertiesDeviceFormatLog extends DefaultFormatLog {

	public GetPropertiesDeviceFormatLog() {

	}

	private static final String STR_ERROR = "Error:";

	@Override
	public String changeLine(String line) {

		if (line.startsWith(STR_ERROR)) {
			LogAdb.error(line);
			return null;
		}
		if (line.matches(AdbConsts.PATTERN_PROPERTY_DEVICE)) {
			LogAdb.debug(line);
			return null;
		}

		return line;
	}

}
