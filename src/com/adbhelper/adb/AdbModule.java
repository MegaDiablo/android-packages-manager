package com.adbhelper.adb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.adbhelper.adb.exseptions.NotAccessPackageManager;

public class AdbModule implements AdbConsts {
	private String fileAdb;

	private boolean ownerAdb = false;
	private static final String MASK_APP = "%app%";
	private static final String MASK_FILE = "%file%";
	private static final String MASK_FROM = "%from%";
	private static final String MASK_TO = "%to%";
	private static final String CONSOLE_STARTED_ADB = "* daemon started successfully *";
	private static final String VALID_ADRESS = ".*:[0-9]{1,5}";
	private static final int DEFAULT_PORT = 5555;
	
	private Properties propertiesActivities;
	private Properties filterActivities;
	private File fileActivities;
	private Process currentProcess;

	public static final String DEFAULT_PATH_ABD = "d:\\androidSDK\\platform-tools\\adb";
	private static final String CMD_UNINSTALL = "uninstall ";
	private static final String CMD_INSTALL = "install %file%";
	private static final String CMD_DEVICES = "devices";
	private static final String CMD_START = "shell am start -n %app%/%activity% -a android.intent.action.MAIN -c android.intent.category.LAUNCHER";
	private static final String CMD_LIST_PACKAGES = "shell pm list packages -f";
	private static final String CMD_REBOOT = "reboot";
	private static final String CMD_WAIT = "wait-for-device";
	private static final String CMD_STOP_ADB = "kill-server";
	private static final String CMD_START_ADB = "start-server";
	private static final String CMD_SEND_KEYCODE = "shell input keyevent ";
	private static final String CMD_DOWNLOAD_FILE = "pull %from% %to%";
	private static final String CMD_CONNECT = "connect %to%";
	private static final String CMD_DISCONNECT = "disconnect %from%";

	private static final String LOG_SEND_KEYCODE = "Sending key code ";
	private static final String LOG_COMPLITE_SEND_KEYCODE = "Sended key code";
	private static final String LOG_UNINSTALL = "Uninstall %app%";
	private static final String LOG_INSTALL = "Install %file%";
	private static final String LOG_UPLOAD = "Uploading \"%file%\" ...";
	private static final String LOG_INSTALL_FAIL = "Install %file% FAIL!";
	private static final String LOG_INSTALL_COMPLITE = "Install %file% COMPLITE";
	private static final String LOG_START = "Start %app%";
	private static final String LOG_LIST_PACKAGES = "List packages:";
	private static final String LOG_REBOOT = "Reboot device";
	private static final String SYSTEM_FILTER = "/system/.*";
	private static final String LOG_START_WAIT = "Waiting device...";
	private static final String LOG_END_WAIT = "Device connected";
	private static final String LOG_END_LIST_PACKAGES = "End list packges.";
	private static final String LOG_STOP_ADB = "Stop ADB";
	private static final String LOG_START_ADB = "Starting ADB...";
	private static final String LOG_RESTART_ADB = "Restart ADB";
	private static final String LOG_END_START_ADB = "Complite start ADB";
	private static final String LOG_DOWNLOAD_FILE = "Start download file ";
	private static final String LOG_END_DOWNLOAD_FILE = "Downloaded file ";
	private static final String LOG_GET_DEVICES = "Getting list devices... ";
	private static final String LOG_START_CONNECT = "Start connecting to \"%to%\" ...";
	private static final String LOG_END_CONNECT = "Connect to \"%to%\" complite";
	private static final String LOG_START_DISCONNECT = "Start disconnecting from \"%from%\" ...";
	private static final String LOG_END_DISCONNECT = "Disconnect from %from% complite";
	private static final String LOG_CONNECT_FAIL = "Connect to \"%to%\" fail";

	public String runCmd(String pathAdb, String device, String[] cmd) {
		return runCmd(pathAdb, device, cmd, null);
	}

	public String runCmd(String device, String string) {
		return runCmd(device, string.split(" "), new DefaultFormatLog());
	}

	public String runCmd(String device, String[] string) {
		return runCmd(device, string, new DefaultFormatLog());
	}

	public String runCmd(String pathAdb, String device, String[] cmd,
			FormatLog formatLog) {
		String[] shellCmd;
		int k = 1;
		String[] firstCmds = {};
		int news = firstCmds.length;
		if (device != null) {
			k = 3;
			shellCmd = new String[cmd.length + k + news];
			shellCmd[1 + news] = "-s";
			shellCmd[2 + news] = device;
		} else {
			shellCmd = new String[cmd.length + k + news];
		}
		for (int i = 0; i < firstCmds.length; i++) {
			shellCmd[i] = firstCmds[i];

		}
		shellCmd[0 + news] = pathAdb;
		// shellCmd += " " + cmd;
		for (int i = 0; i < cmd.length; i++) {
			shellCmd[i + k + news] = cmd[i];
		}

		if (formatLog == null)
			formatLog = new DefaultFormatLog();

		return exec(shellCmd, formatLog);
	}

	public AdbModule(String fileAdb, Properties listActivities) {
		super();
		this.fileAdb = fileAdb;
		this.propertiesActivities = listActivities;

	}

	public AdbModule(String fileAdb) {
		this(fileAdb, (Properties) null);
	}

	public AdbModule(String fileAdb, String fileNameListActivities)
			throws IOException {
		this(fileAdb, new File(fileNameListActivities));

	}

	public AdbModule(String fileAdb, File fileListActivities)
			throws IOException {
		this(fileAdb, (Properties) null);
		this.propertiesActivities = new Properties();
		loadListActivities(fileListActivities);

	}

	public void loadListActivities(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		fileActivities = file;
		propertiesActivities.load(new FileInputStream(file));
	}

	public AdbModule loadFilterActivities(String fileName) throws IOException {
		return loadFilterActivities(new File(fileName));

	}

	public AdbModule loadFilterActivities(File file) throws IOException {
		if (file.exists()) {
			Properties prop = new Properties();
			prop.load(new FileInputStream(file));
			setFilterActivities(prop);
		}
		return this;
	}

	public AdbModule() {
		this(DEFAULT_PATH_ABD);
	}

	public String runCmd(String device, String[] cmd, FormatLog formatLog) {
		return runCmd(fileAdb, device, cmd, formatLog);
	}

	/**
	 * 
	 * @return SUCCESS
	 */
	public int uninstall(String device, String app) {
		LogAdb.info(LOG_UNINSTALL.replace(MASK_APP, app));
		runCmd(device, CMD_UNINSTALL + app);
		return AdbConsts.SUCCESS;
	}

	/**
	 * 
	 * @return SUCCESS
	 */
	public int downloadFile(String device, String fromPath, String toPath) {
		LogAdb.info(LOG_DOWNLOAD_FILE + fromPath);

		if (toPath == null) {
			toPath = "";
		}
		String[] cmds = CMD_DOWNLOAD_FILE.split(" ");
		for (int i = 0; i < cmds.length; i++) {
			cmds[i] = cmds[i].replace(MASK_FROM, fromPath).replace(MASK_TO,
					toPath);
		}
		runCmd(device, cmds);
		LogAdb.info(LOG_END_DOWNLOAD_FILE + toPath);
		return AdbConsts.SUCCESS;
	}

	public int install(String device, String pathApp) {
		LogAdb.info(LOG_INSTALL.replace(MASK_FILE, pathApp));
		LogAdb.info(LOG_UPLOAD.replace(MASK_FILE, pathApp));
		String[] cmds = CMD_INSTALL.split(" ");
		for (int i = 0; i < cmds.length; i++) {
			cmds[i] = cmds[i].replace(MASK_FILE, pathApp);
		}
		String[] res = runCmd(device, cmds, new InstallFormatLog())
				.split("\\n");
		if (res.length < 1) {
			LogAdb.error(LOG_INSTALL_FAIL.replace(MASK_FILE, pathApp));
			return FAILTURE;
		}
		if (res[res.length - 1].equals(STR_FAILTURE)) {
			LogAdb.error(LOG_INSTALL_FAIL.replace(MASK_FILE, pathApp));
			return FAILTURE;
		}
		LogAdb.info(LOG_INSTALL_COMPLITE.replace(MASK_FILE, pathApp));
		return AdbConsts.SUCCESS;
	}

	public void reinstall(String device, String app, String activity,
			String pathApp) {
		uninstall(device, app);
		install(device, pathApp);
		startActivity(device, app, activity);
	}

	public void startActivity(String device, String app, String activity) {
		LogAdb.info(LOG_START.replace(MASK_APP, app));
		runCmd(device,
				CMD_START.replace(MASK_APP, app)
						.replace("%activity%", activity).split(" "),
				new StartFormatLog());
	}

	public void waitDevice() {
		LogAdb.info(LOG_START_WAIT);
		runCmd(null, CMD_WAIT.split(" "));
		LogAdb.info(LOG_END_WAIT);
	}

	public List<AdbDevice> devices() {
		LogAdb.info(LOG_GET_DEVICES);
		String ss[] = runCmd(null, CMD_DEVICES).split("\\n");
		List<AdbDevice> devices = new ArrayList<AdbDevice>();
		for (int i = 1; i < ss.length; i++) {
			String[] tmp = ss[i].split("\\t");
			if (tmp.length < 2) {
				continue;
			}
			devices.add(new AdbDevice(tmp[0], tmp[1], this));
		}

		return devices;
	}

	public List<AdbPackage> getPackages(String device)
			throws NotAccessPackageManager {

		return getPackages(device, false);
	}

	public List<AdbPackage> getPackagesWithSystem(String device)
			throws NotAccessPackageManager {

		return getPackages(device, null);
	}

	public List<AdbPackage> getPackagesNonSystem(String device)
			throws NotAccessPackageManager {
		return getPackages(device, SYSTEM_FILTER);
	}

	public List<AdbPackage> getPackages(String device, boolean withSystem)
			throws NotAccessPackageManager {
		if (withSystem) {
			return getPackagesWithSystem(device);
		} else {
			return getPackagesNonSystem(device);
		}
	}

	public List<AdbPackage> getPackages(String device, String fileIgnoreFilter)
			throws NotAccessPackageManager {
		LogAdb.info(LOG_LIST_PACKAGES);
		String result[] = runCmd(device, CMD_LIST_PACKAGES.split(" "),
				new PackagesFormatLog(fileIgnoreFilter)).split("\\n");
		if (result.length > 0) {
			if (result[result.length - 1]
					.equals(AdbConsts.STR_ERROR_NOT_ACCESS_PACKAGE_MANAGER)) {
				throw new NotAccessPackageManager();
			}
		}
		List<AdbPackage> packages = new ArrayList<AdbPackage>();
		for (int i = 0; i < result.length; i++) {
			if (result[i].equals("")) {
				continue;
			}
			String[] tmp = result[i].replaceAll(".*:", "").split("=");
			if (tmp.length < 2) {
				continue;
			}
			if ((fileIgnoreFilter == null)
					|| (!tmp[0].matches(fileIgnoreFilter)))
				packages.add(new AdbPackage(this, tmp[1], tmp[0], device));
		}
		LogAdb.info(LOG_END_LIST_PACKAGES);
		return packages;
	}

	public void reboot(String device) {
		LogAdb.info(LOG_REBOOT);
		runCmd(device, CMD_REBOOT.split(" "));
	}

	public void reboot(AdbDevice device) {
		reboot(device.getName());
	}

	public void sendKeyCode(String device, int keyCode) {
		LogAdb.info(LOG_SEND_KEYCODE + keyCode);
		runCmd(device, CMD_SEND_KEYCODE + keyCode);
		LogAdb.info(LOG_COMPLITE_SEND_KEYCODE);
	}

	public void sendKeyCode(AdbDevice device, int keyCode) {
		sendKeyCode(device.getName(), keyCode);
	}

	public void stop() {
		if (existProcess()) {
			stopCurrentProcess();
		}
		LogAdb.info(LOG_STOP_ADB);
		runCmd(null, CMD_STOP_ADB);
	}


	public int connet(String address) {
		LogAdb.info(LOG_START_CONNECT.replace(MASK_TO, address));
		if (!address.matches(VALID_ADRESS))
		{
			address+=":"+DEFAULT_PORT;
		}
		String[] res = runCmd(null, CMD_CONNECT.replace(MASK_TO, address)).split("\\n");;
		if (res[res.length - 1].matches(STR_CONNECT_COMPLITE)) {
			LogAdb.info(LOG_END_CONNECT.replace(MASK_TO, address));
			return SUCCESS;
		} else {
			LogAdb.error(LOG_CONNECT_FAIL.replace(MASK_TO, address));
			return FAILTURE;
		}
		
	}
	
	public void disconnet(String address) {
		LogAdb.info(LOG_START_DISCONNECT.replace(MASK_FROM, address));
		runCmd(null, CMD_DISCONNECT.replace(MASK_FROM, address));
		LogAdb.info(LOG_END_DISCONNECT.replace(MASK_FROM, address));
	}
	
	public void start() {
		LogAdb.info(LOG_START_ADB);
		runCmd(null, CMD_START_ADB);
		LogAdb.info(LOG_END_START_ADB);
	}

	public void restart() {
		LogAdb.info(LOG_RESTART_ADB);
		stop();
		start();
	}

	public String execOld(String[] cmds, FormatLog formatLog) {
		Runtime run = Runtime.getRuntime();
		String res = "";

		try {
			// System.out.print("["+new
			// Date(System.currentTimeMillis()).toLocaleString()+"] : ");
			// System.out.print(new Time(System.currentTimeMillis())+" : ");
			// System.out.println("Exec " + cmd);
			// LogAdb.info("Exec " + cmd);
			// LogAdb.info("start process");
			currentProcess = run.exec(cmds);
			AdbConsoleThread consoleThread = new AdbConsoleThread(
					currentProcess, formatLog);

			consoleThread.start();

			currentProcess.waitFor();
			Thread.sleep(10);
			// LogAdb.info("end process");
			// currentProcess.destroy();
			currentProcess = null;
			consoleThread.endConsole();
			// LogAdb.info("console process");
			res = consoleThread.getStringConsole();
			ownerAdb = ownerAdb || res.contains(CONSOLE_STARTED_ADB);

			// LogAdb.info("res process");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return res;
	}

	public boolean existProcess() {
		return currentProcess != null;
	}

	public boolean stopCurrentProcess() {
		LogAdb.info("Stoped Curent Process");
		if (!existProcess()) {
			LogAdb.error("Process not started");
			return false;
		}
		currentProcess.destroy();
		return true;

	}

	/**/

	public String exec(String[] params, FormatLog formatLog) {
		return execOld(params, formatLog);
	}

	public String getNameActivity(String packageName) {
		String activity = getNameActivityPropperty(packageName);

		if (activity == null) {
			return getFilterValue(packageName);
		}
		return activity;
	}

	public String getNameActivityPropperty(String packageName) {
		if (propertiesActivities == null) {
			return null;
		}
		return propertiesActivities.getProperty(packageName);
	}

	public void setNameActivity(String packageName, String nameActivity)
			throws IOException {
		if (propertiesActivities == null) {
			return;
		}

		propertiesActivities.setProperty(packageName, nameActivity);
		if (fileActivities != null) {
			propertiesActivities
					.store(new FileOutputStream(fileActivities), "");
		}
	}

	public String getFilterValue(String name) {
		if (filterActivities == null) {
			return null;
		}
		// String key = null;
		String tmp = null;
		Enumeration<Object> keys = filterActivities.keys();
		while (keys.hasMoreElements()) {
			tmp = String.valueOf(keys.nextElement());
			if (name.matches(tmp)) {

				return filterActivities.getProperty(tmp);
			}

		}
		return null;
	}

	public Properties getFilterActivities() {
		return filterActivities;
	}

	public void setFilterActivities(Properties filterActivities) {
		this.filterActivities = filterActivities;
	}

	public boolean isOwnerAdb() {
		return ownerAdb;
	}

	public void finishAdb() {
		if (existProcess()) {
			stopCurrentProcess();
		}
		if (isOwnerAdb()) {
			stop();
		}
	}
}
