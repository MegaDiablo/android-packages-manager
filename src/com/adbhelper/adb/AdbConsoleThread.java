package com.adbhelper.adb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
					LogAdb.info(formatLog.changeLine(line));

				}
				line = buf.readLine();
			}

			pr.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res.toString();
	}
	public void newRun() {
		bufferedReader = new BufferedReader(new InputStreamReader(
				currentProcess.getInputStream()));
		String line = "";
		try {
			do {
				line = bufferedReader.readLine();
				if ((line != null) && (!line.equals(""))) {
					console.append(line).append("\n");
					LogAdb.info(formatLog.changeLine(line));

				}

			} while ((isLive()) && (bufferedReader.ready()));
			// LogAdb.info("!!!!!!!!!endConsole");
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
					LogAdb.info(formatLog.changeLine(line));

				}

			}

			// LogAdb.info("endConsole");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean isLive() {
		return live;
	}

	public void endConsole() {
		live = false;
	}

	public String getStringConsole() {
		return console.toString();
	}
}
