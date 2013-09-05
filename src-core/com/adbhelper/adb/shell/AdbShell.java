package com.adbhelper.adb.shell;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Vladimir Baraznovsky
 *
 */
public class AdbShell {
	private final Process mProcess;

	public AdbShell(final Process pProcess) {
		super();
		mProcess = pProcess;
	}

	public Process getProcess() {
		return mProcess;
	}

	public void destroy() {
		mProcess.destroy();
	}

	public InputStream getErrorStream() {
		return mProcess.getErrorStream();
	}

	public InputStream getInputStream() {
		return mProcess.getInputStream();
	}

	public OutputStream getOutputStream() {
		return mProcess.getOutputStream();
	}

}
