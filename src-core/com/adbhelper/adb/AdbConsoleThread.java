package com.adbhelper.adb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.adbhelper.adb.log.FormatLog;
import com.adbhelper.adb.log.LogAdb;

public class AdbConsoleThread extends Thread {

	private Process currentProcess;
	private StringBuffer console;
	private FormatLog formatLog;
	private boolean live = true;

	public AdbConsoleThread(Process currentProcess, FormatLog formatLog) {
		super("AdbConsoleThread");
		this.currentProcess = currentProcess;
		this.console = new StringBuffer();
		this.formatLog = formatLog;
	}

	private BufferedReader bufferedReader;

	@Deprecated
	public static String execNew(String cmd, FormatLog formatLog) {
		Runtime run = Runtime.getRuntime();
		StringBuffer res = new StringBuffer();
		Process pr;
		try {
			// System.out.print("["+new
			// Date(System.currentTimeMillis()).toLocaleString()+"] : ");
			// System.out.print(new Time(System.currentTimeMillis())+" : ");
			// System.out.println("Exec " + cmd);
			// LogAdb.info("Exec " + cmd);
			pr = run.exec(cmd);

			InputStreamReader in = new InputStreamReader(pr.getInputStream());

			BufferedReader buf = new BufferedReader(in);

			String line = buf.readLine();
			while ((line != null)) {
				if (!line.equals("")) {
					res.append(line).append("\n");
					formatLog.info(formatLog.changeLine(line));

				}
				line = buf.readLine();
			}

			pr.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return res.toString();
	}

	@Deprecated
	public void newRun() {
		bufferedReader = new BufferedReader(new InputStreamReader(
				currentProcess.getInputStream()));
		String line = "";
		try {
			do {
				line = bufferedReader.readLine();
				if ((line != null) && (!line.equals(""))) {
					console.append(line).append("\n");
					formatLog.info(formatLog.changeLine(line));

				}

			} while ((isLive()) && (bufferedReader.ready()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		bufferedReader = new BufferedReader(new InputStreamReader(
				currentProcess.getInputStream()));
		String line = "";
		try {
			while ((isLive()) && ((line = bufferedReader.readLine()) != null)) {
				if (!line.equals("")) {
					console.append(line).append("\n");
					formatLog.info(formatLog.changeLine(line));

				}

			}

			// LogAdb.info("endConsole");
		} catch (IOException e) {
			e.printStackTrace();
		}
		endConsole();
	}

	private boolean isLive() {
		return live;
	}

	public void endConsole() {
		live = false;
		synchronized (this) {
			this.notifyAll();
		}
	}

	public String getStringConsole() {
		return console.toString();
	}
}
