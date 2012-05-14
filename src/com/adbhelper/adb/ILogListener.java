package com.adbhelper.adb;
/**
 *
 * @author Vladimir Baraznovsky
 *
 */
public interface ILogListener {
		void onInfo(long time,String message);
		void onError(long time,String message);
		void onDebug(long time,String message);
}
