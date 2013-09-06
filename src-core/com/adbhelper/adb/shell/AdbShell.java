package com.adbhelper.adb.shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author Vladimir Baraznovsky
 *
 */
public class AdbShell {
	private final Process mProcess;
	private final OutputStreamWriter mWriter;
	private final InputStreamReader mReader;

	public AdbShell(final Process pProcess) {
		super();
		mProcess = pProcess;
		mWriter = new OutputStreamWriter(pProcess.getOutputStream());
		mReader = new InputStreamReader(pProcess.getInputStream());
	}

	public Process getProcess() {
		return mProcess;
	}

	public void destroy() {
		mProcess.destroy();
		if (getWriter() != null) {
			try {
				getWriter().close();
			} catch (IOException e) {
			}
		}
		if (getReader() != null) {
			try {
				getReader().close();
			} catch (IOException e) {
			}
		}

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

	public OutputStreamWriter getWriter() {
		return mWriter;
	}

	public InputStreamReader getReader() {
		return mReader;
	}

	public void pushKeys(final char... ds) throws IOException {
		mWriter.write(ds);
		mWriter.flush();
	}

	public void postString(final String pString) throws IOException {
		write(pString);
		mWriter.flush();
	}

	public void write(final String pString) throws IOException {
		if (pString != null) {
			mWriter.write(pString);
		}
	}
}
