package com.adbhelper.adb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import com.adbhelper.adb.exceptions.AdbError;
import com.adbhelper.adb.exceptions.NotAccessPackageManager;
import com.adbhelper.adb.exceptions.NotFoundActivityException;
import com.adbhelper.adb.exceptions.install.InstallException;
import com.adbhelper.adb.log.DefaultFormatLog;
import com.adbhelper.adb.log.FormatLog;
import com.adbhelper.adb.log.GetInfoApkFormatLog;
import com.adbhelper.adb.log.GetPropertiesDeviceFormatLog;
import com.adbhelper.adb.log.InstallFormatLog;
import com.adbhelper.adb.log.LogAdb;
import com.adbhelper.adb.log.PackagesFormatLog;
import com.adbhelper.adb.log.StartFormatLog;
import com.adbhelper.adb.shell.AdbShell;

public class AdbModule implements AdbConsts {

	public static final String VERSION_ADB_HELPER = "%%VERSION_CORE%%";
	private static final String NAME_RESOURCE_LABELS_PERMISSIONS = "com.adbhelper.adb.permissions";
	private static final long CONSOLE_TIMEOUT = 5000;
	private static final boolean DEFAULT_AUTOSTART_AFTER_INSTALL = false;
	private static final String DEFAULT_FORMAT_INFO_PACKAGE = "%app%[ - (%label%)]";
	private String fileAdb;
	private String fileAapt;

	private boolean ownerAdb = false;
	private static final String MASK_RPOPERTY_NAME_LABEL = "%s:label";
	private static final String MASK_APP = "%app%";
	private static final String MASK_LABEL = "%label%";
	private static final String MASK_FILE = "%file%";
	private static final String MASK_FROM = "%from%";
	private static final String MASK_TO = "%to%";
	private static final String MASK_MESSAGE = "%message%";
	private static final String MASK_COUNT = "%count%";
	private static final String MASK_ACTIVITY = "%activity%";
	private static final String CONSOLE_STARTED_ADB = "* daemon started successfully *";
	private static final String VALID_ADRESS = ".*:[0-9]{1,5}";

	private static final int DEFAULT_PORT = 5555;

	private Properties propertiesActivities;
	private Properties filterActivities;
	private File fileActivities;
	private Process currentProcess;

	private ResourceBundle labelsPermissions;

	// public static final String DEFAULT_PATH_ABD =
	// "d:\\androidSDK\\platform-tools\\adb";
	public static final String DEFAULT_PATH_ABD = "adb";
	public static final String DEFAULT_PATH_AAPT = "aapt";
	private static final String CMD_UNINSTALL = "uninstall ";
	private static final String CMD_GET_INFO_APK = "dump badging %file%";
	private static final String CMD_INSTALL = "install %file%";
	private static final String CMD_REINSTALL = "install -r %file%";
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
	private static final String CMD_MONKEY = "shell monkey -v -p %app% -c android.intent.category.LAUNCHER %count%";
	private static final String CMD_CLEAR_TMP = "shell rm /data/local/tmp/*";
	private static final String CMD_DEBUG = "shell am start -D -n %app%/%activity% -a android.intent.action.MAIN -c android.intent.category.LAUNCHER";

	private static final String CMD_GET_PROP = "shell getprop";
	private static final String CMD_CLEAR_DATA = "shell pm clear %app%";
	private static final String CMD_SHELL = "shell";
	private static final String CMD_RUN_AS = "run-as %app%";


	private static final String LOG_SEND_KEYCODE = "Sending key code ";
	private static final String LOG_COMPLITE_SEND_KEYCODE = "Sended key code";
	private static final String LOG_UNINSTALL = "Uninstall %app%";
	private static final String LOG_INSTALL = "Install %file%";
	private static final String LOG_UPLOAD = "Uploading \"%file%\" ...";
	private static final String LOG_INSTALL_FAIL = "Install %file% FAIL!";
	private static final String LOG_INSTALL_FAIL_MESSAGE = "Install %file% FAIL %message%!";
	private static final String LOG_INSTALL_COMPLITE = "Install %file% COMPLITE";
	private static final String LOG_START = "Start %app%";
	private static final String LOG_LIST_PACKAGES = "List packages:";
	private static final String LOG_REBOOT = "Reboot device";
	private static final String SYSTEM_FILTER = "/system/.*";
	private static final String LOG_START_WAIT = "Waiting device...";
	private static final String LOG_END_WAIT = "Device connected";
	private static final String LOG_END_LIST_DEVICES = "End list of devices.";
	private static final String LOG_END_LIST_PACKAGES = "End list of packges.";
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
	private static final String LOG_GET_INFO_APK = "GETTING INFORMATION about %file% ...";
	private static final String LOG_GET_INFO_APK_END = "READ INFORMATION COMPLITE";
	private static final String LOG_START_GET_PACKAGES = "Getting packages...";
	private static final String LOG_COUNT_PACKAGES = "Founded %s packages";
	private static final String LOG_MONKEY = "Start monkey for %app%";
	private static final String LOG_INIT_ADBMODULE = "Initialize ADB Helper version %s.";
	private static final String LOG_CLEAR_TMP_START = "Start clear temp(/data/local/tmp)";
	private static final String LOG_CLEAR_TMP_END = "Clear temp complite";

	private static final String LOG_GET_PROPERTIES_START = "Getting device's properties from %s...";
	private static final String LOG_GET_PROPERTIES_END = "Completed getting device's properties ";

	private static final String LOG_DEBUG = "Start debug %app%";
	private static final String LOG_CLEAR_DATA = "Delete all data associated with %app%";
	private static final String LOG_START_SHELL = "Start shell";

	private final LogAdb logAdb=new LogAdb();

	public AdbModule(final String fileAdb, String fileAapt, final Properties listActivities) {
		super();
		this.fileAdb = fileAdb;
		if ((fileAapt == null) || ("".equals(fileAapt))) {

			int index = fileAdb.lastIndexOf(File.separator);
			String path = "";
			if (index > 0) {
				path = fileAdb.substring(0, index + 1);
			}
			fileAapt = path + DEFAULT_PATH_AAPT;
		}

		this.fileAapt = fileAapt;
		this.propertiesActivities = listActivities;
		logAdb.printInfo(LOG_INIT_ADBMODULE, VERSION_ADB_HELPER);

	}

	public AdbModule(final String fileAdb) {
		this(fileAdb, null, (Properties) null);
	}

	public AdbModule(final String fileAdb, final String fileAapt,
			final String fileNameListActivities) throws IOException {
		this(fileAdb, fileAapt, new File(fileNameListActivities));

	}

	public AdbModule(final String fileAdb, final String fileAapt, final File fileListActivities)
			throws IOException {
		this(fileAdb, fileAapt, new Properties());
		loadListActivities(fileListActivities);

	}

	@Deprecated
	public AdbModule(final String fileAdb, final String fileNameListActivities)
			throws IOException {
		this(fileAdb, null, new File(fileNameListActivities));

	}

	@Deprecated
	public AdbModule(final String fileAdb, final File fileListActivities)
			throws IOException {
		this(fileAdb, null, fileListActivities);

	}

	public String runCmd(final String path, final String device, final String[] cmd) {
		return runCmd(path, device, cmd, null);
	}

	public String runAdb(final String device, final String string) {
		return runAdb(device, string.split(" "), new DefaultFormatLog(logAdb));
	}

	public String runAdb(final String device, final String[] string) {
		return runAdb(device, string, new DefaultFormatLog(logAdb));
	}

	public String runCmd(final String pathAdb, final String device, final String[] cmd,
			FormatLog formatLog) {
		String[] shellCmd = createShellCmd(pathAdb, device, cmd);

		if (formatLog == null)
			formatLog = new DefaultFormatLog(logAdb);

		return exec(shellCmd, formatLog);
	}

	private String[] createShellCmd(final String pathAdb,
			final String device,
			final String[] cmd) {
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
		return shellCmd;
	}

	public void loadListActivities(final File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		fileActivities = file;
		propertiesActivities.load(new FileInputStream(file));
	}

	public AdbModule loadFilterActivities(final String fileName) throws IOException {
		return loadFilterActivities(new File(fileName));

	}

	public AdbModule loadFilterActivities(final File file) throws IOException {
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

	public String runAdb(final String device, final String[] cmd, final FormatLog formatLog) {
		return runCmd(fileAdb, device, cmd, formatLog);
	}

	public String runAapt(final String[] cmd, final FormatLog formatLog) {
		return runCmd(fileAapt, null, cmd, formatLog);
	}

	public String runAapt(final String[] cmd) {
		return runCmd(fileAapt, null, cmd);
	}

	public String runAapt(final String cmd) {
		return runCmd(fileAapt, null, cmd.split(" "));
	}

	/**
	 *
	 * @return SUCCESS
	 */
	public int uninstall(final String device, final String app) {
		logAdb.info(LOG_UNINSTALL.replace(MASK_APP, app));
		runAdb(device, CMD_UNINSTALL + app);
		return AdbConsts.SUCCESS;
	}

	/**
	 *
	 * @return SUCCESS
	 */
	public int clearData(final String device, final String app) {
		logAdb.info(LOG_CLEAR_DATA.replace(MASK_APP, app));
		runAdb(device, CMD_CLEAR_DATA.replace(MASK_APP, app));
		return AdbConsts.SUCCESS;
	}


	/**
	 *
	 * @return SUCCESS
	 */
	public int downloadFile(final String device, final String fromPath, String toPath) {
		logAdb.info(LOG_DOWNLOAD_FILE + fromPath);

		if (toPath == null) {
			toPath = "";
		}
		String[] cmds = CMD_DOWNLOAD_FILE.split(" ");
		for (int i = 0; i < cmds.length; i++) {
			cmds[i] = cmds[i].replace(MASK_FROM, fromPath).replace(MASK_TO,
					toPath);
		}
		runAdb(device, cmds);
		logAdb.info(LOG_END_DOWNLOAD_FILE + toPath);
		return AdbConsts.SUCCESS;
	}

	public String reinstall(final String device, final String pathApp)
			throws InstallException {
		return reinstall(device, pathApp, DEFAULT_AUTOSTART_AFTER_INSTALL);
	}

	public String reinstall(final String device, final String pathApp, final boolean autoStart)
			throws InstallException {
		return install(CMD_REINSTALL, device, pathApp, autoStart);
	}

	/**
	 * Install package into device.<br/>
	 * Recommend for use {@link AdbModule#install(String, String, boolean)}
	 *
	 * @param device
	 *            - target device
	 * @param pathApp
	 *            - path of file
	 * @return Name of installed package
	 */
	@Deprecated
	public String install(final String device, final String pathApp)
			throws InstallException {
		return install(device, pathApp, DEFAULT_AUTOSTART_AFTER_INSTALL);
	}

	/**
	 * Install package into device.
	 *
	 * @param device
	 *            - target device
	 * @param pathApp
	 *            - path of file
	 * @param autoStart
	 *            - start package after install
	 * @return Name of installed package
	 */
	public String install(final String device, final String pathApp, final boolean autoStart)
			throws InstallException {
		return install(CMD_INSTALL, device, pathApp, autoStart);
	}

	protected String install(final String cmd, final String device, final String pathApp,
			final boolean autoStart) throws InstallException {
		logAdb.info(LOG_INSTALL.replace(MASK_FILE, pathApp));
		logAdb.info(LOG_UPLOAD.replace(MASK_FILE, pathApp));
		String[] cmds = cmd.split(" ");
		for (int i = 0; i < cmds.length; i++) {
			cmds[i] = cmds[i].replace(MASK_FILE, pathApp);
		}
		String[] res = null;
		try {
			res = runAdb(device, cmds, new InstallFormatLog(logAdb)).split("\\n");
		} catch (AdbError e) {
			logAdb.error(LOG_INSTALL_FAIL.replace(MASK_FILE, pathApp));
			throw InstallException.createInstallException(e);

		}
		if ((res == null) || (res.length < 1)) {
			logAdb.error(LOG_INSTALL_FAIL.replace(MASK_FILE, pathApp));
			throw InstallException.createInstallException((String) null);
		}
		if (res[res.length - 1].startsWith(STR_FAILTURE)) {
			String message = res[res.length - 1];

			String errorMessage = AdbUtils
					.getResultPattern(message, "\\[.*\\]");

			logAdb.error(LOG_INSTALL_FAIL_MESSAGE.replace(MASK_FILE, pathApp)
					.replace(MASK_MESSAGE, errorMessage));
			throw InstallException.createInstallException(errorMessage);
			// return FAILTURE;
		}
		logAdb.info(LOG_INSTALL_COMPLITE.replace(MASK_FILE, pathApp));
		AdbPackage adbPackage = getInfoApk(pathApp);
		if (autoStart) {
			adbPackage.setDevice(device);
			try {
				adbPackage.start();
			} catch (NotFoundActivityException e) {

			}
		}
		return adbPackage.getName();
	}

	/**
	 * Use {@link AdbModule#reinstall(String, String, boolean)}
	 */
	@Deprecated
	public void reinstall(final String device, final String app, final String activity,
			final String pathApp) throws InstallException {
		uninstall(device, app);
		install(device, pathApp);
		startActivity(device, app, activity);
	}

	public void startActivity(final String device, final String app, final String activity) {
		logAdb.info(LOG_START.replace(MASK_APP, app));
		runAdb(device,
				CMD_START.replace(MASK_APP, app)
						.replace(MASK_ACTIVITY, activity).split(" "),
				new StartFormatLog(logAdb));
	}

	public void debugActivity(final String device, final String app, final String activity) {
		logAdb.info(LOG_DEBUG.replace(MASK_APP, app));
		runAdb(device,
				CMD_DEBUG.replace(MASK_APP, app)
						.replace(MASK_ACTIVITY, activity).split(" "),
				new StartFormatLog(logAdb));
	}

	public void waitDevice() {
		logAdb.info(LOG_START_WAIT);
		runAdb(null, CMD_WAIT.split(" "));
		logAdb.info(LOG_END_WAIT);
	}

	public List<AdbDevice> devices() {
		logAdb.info(LOG_GET_DEVICES);
		String ss[] = runAdb(null, CMD_DEVICES).split("\\n");
		List<AdbDevice> devices = new ArrayList<AdbDevice>();
		for (int i = 1; i < ss.length; i++) {
			String[] tmp = ss[i].split("\\t");
			if (tmp.length < 2) {
				continue;
			}
			devices.add(new AdbDevice(tmp[0], tmp[1], this));
		}
		logAdb.info(LOG_END_LIST_DEVICES);
		return devices;
	}

	public List<AdbPackage> getPackages(final String device)
			throws NotAccessPackageManager {

		return getPackages(device, false);
	}

	public List<AdbPackage> getPackagesWithSystem(final String device)
			throws NotAccessPackageManager {

		return getPackages(device, null);
	}

	public List<AdbPackage> getPackagesNonSystem(final String device)
			throws NotAccessPackageManager {
		return getPackages(device, SYSTEM_FILTER);
	}

	public List<AdbPackage> getPackages(final String device, final boolean withSystem)
			throws NotAccessPackageManager {
		if (withSystem) {
			return getPackagesWithSystem(device);
		} else {
			return getPackagesNonSystem(device);
		}
	}

	public List<AdbPackage> getPackages(final String device, final String fileIgnoreFilter)
			throws NotAccessPackageManager {
		// logAdb.info(LOG_LIST_PACKAGES);
		logAdb.info(LOG_START_GET_PACKAGES);
		String result[] = runAdb(device, CMD_LIST_PACKAGES.split(" "),
				new PackagesFormatLog(logAdb,fileIgnoreFilter)).split("\\n");
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
		logAdb.info(LOG_COUNT_PACKAGES, packages.size());
		// logAdb.info(LOG_END_LIST_PACKAGES);
		return packages;
	}

	public void reboot(final String device) {
		logAdb.info(LOG_REBOOT);
		runAdb(device, CMD_REBOOT.split(" "));
	}

	public void reboot(final AdbDevice device) {
		reboot(device.getName());
	}

	public void sendKeyCode(final String device, final int keyCode) {
		logAdb.info(LOG_SEND_KEYCODE + keyCode);
		runAdb(device, CMD_SEND_KEYCODE + keyCode);
		logAdb.info(LOG_COMPLITE_SEND_KEYCODE);
	}

	public void sendKeyCode(final AdbDevice device, final int keyCode) {
		sendKeyCode(device.getName(), keyCode);
	}

	public void stop() {
		if (existProcess()) {
			stopCurrentProcess();
		}
		logAdb.info(LOG_STOP_ADB);
		runAdb(null, CMD_STOP_ADB);
	}

	public int connet(String address) {
		logAdb.info(LOG_START_CONNECT.replace(MASK_TO, address));
		if (!address.matches(VALID_ADRESS)) {
			address += ":" + DEFAULT_PORT;
		}
		String[] res = runAdb(null, CMD_CONNECT.replace(MASK_TO, address))
				.split("\\n");
		;
		if (res[res.length - 1].matches(STR_CONNECT_COMPLITE)) {
			logAdb.info(LOG_END_CONNECT.replace(MASK_TO, address));
			return SUCCESS;
		} else {
			logAdb.error(LOG_CONNECT_FAIL.replace(MASK_TO, address));
			return FAILTURE;
		}

	}

	public void disconnet(final String address) {
		logAdb.info(LOG_START_DISCONNECT.replace(MASK_FROM, address));
		runAdb(null, CMD_DISCONNECT.replace(MASK_FROM, address));
		logAdb.info(LOG_END_DISCONNECT.replace(MASK_FROM, address));
	}

	public void start() {
		logAdb.info(LOG_START_ADB);
		runAdb(null, CMD_START_ADB);
		logAdb.info(LOG_END_START_ADB);
	}

	public void restart() {
		logAdb.info(LOG_RESTART_ADB);
		stop();
		start();
	}

	/**
	 * start process "Monkey"
	 *
	 * @param device
	 *            - device name
	 * @param app
	 *            - package name
	 * @param count
	 *            - count events
	 */
	public void monkey(final String device, final String app, final int count) {
		logAdb.info(LOG_MONKEY.replace(MASK_APP, app));
		String cmd = CMD_MONKEY.replace(MASK_APP, app);
		cmd = cmd.replace(MASK_COUNT, String.valueOf(count));
		runAdb(device, cmd);

	}

	public void clearTemp(final String device) {
		logAdb.info(LOG_CLEAR_TMP_START);
		runAdb(device, CMD_CLEAR_TMP.split(" "));
		logAdb.info(LOG_CLEAR_TMP_END);
	}

	public void clearTemp(final AdbDevice device) {
		clearTemp(device.getName());
	}

	public Map<String, String> getPropertiesDevice(final String device) {

		logAdb.info(String.format(LOG_GET_PROPERTIES_START, device));
		String[] res = runAdb(device, CMD_GET_PROP.split(" "),
				new GetPropertiesDeviceFormatLog(logAdb)).split("\\n");
		Map<String, String> map = new HashMap<String, String>();
		for (String string : res) {

			if (string.matches(AdbConsts.PATTERN_PROPERTY_DEVICE)) {
				String[] str = AdbUtils.getResultsPattern(string,
						AdbConsts.PATTERN_PROPERTY_DEVICE);
				if (str.length > 1) {
					map.put(str[0], str[1]);
				}
			}
		}
		// logAdb.info(LOG_GET_PROPERTIES_END);
		return map;

	}

	public Map<String, String> getPropertiesDevice(final AdbDevice device) {
		return getPropertiesDevice(device.getName());
	}

	public AdbPackage getInfoPackage(final AdbPackage adbPackage) {
		AdbPackage info = null;

		File tmpFile = AdbUtils.generateTempFile("apm_package", ".apk");
		String tmpFileName = tmpFile.getAbsolutePath();
		downloadFile(adbPackage.getDevice(), adbPackage.getFileName(),
				tmpFileName);
		info = getInfoApk(tmpFileName);
		if (tmpFile.exists()) {
			tmpFile.delete();
		}
		return info;
	}

	public void updatePackages(final List<AdbPackage> adbPackages) {
		updatePackages(adbPackages, false);
	}

	public void updatePackages(final List<AdbPackage> adbPackages,
			final boolean alwaysUpdate) {
		for (AdbPackage adbPackage : adbPackages) {
			updatePackage(adbPackage, alwaysUpdate);
		}
	}

	public void updatePackage(final AdbPackage adbPackage) {
		updatePackage(adbPackage, true);
	}

	public void updatePackage(final AdbPackage adbPackage, final boolean alwaysUpdate) {
		if (alwaysUpdate || (adbPackage.getLabel() == null)
				|| (adbPackage.getDefaultActivity() == null)) {
			try {
				adbPackage.updateInfo(getInfoPackage(adbPackage));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}


	public AdbShell createShellRunAs(final String device,
			final String packageName,
			final String cmd) {
		String[] cmds = cmd.split(" ");
		return createShellRunAs(device, packageName, cmds);
	}

	public AdbShell createShellRunAs(final String device,
			final String packageName,
			final String... cmd) {

		String[] cmdRunAs = CMD_RUN_AS.split(" ");
		for (int i = 0; i < cmdRunAs.length; i++) {
			cmdRunAs[i] = cmdRunAs[i].replace(MASK_APP, packageName);
		}
		String[] shellCmd = new String[cmd.length + cmd.length];
		for (int i = 0; i < cmdRunAs.length; i++) {
			shellCmd[i] = cmdRunAs[i];
		}
		for (int i = 0; i < cmd.length; i++) {
			shellCmd[cmdRunAs.length + i] = cmd[i];
		}
		return runProcess(fileAdb, device, shellCmd);
	}

	public AdbShell createShell(final String device,
			final String cmd) {
		String[] cmds = cmd.split(" ");
		return createShell(device, cmds);
	}
	public AdbShell createShell(final String device, final String... cmd) {
		String[] shellCmd = new String[cmd.length + 1];
		shellCmd[0] = CMD_SHELL;
		for (int i = 0; i < cmd.length; i++) {
			shellCmd[i + 1] = cmd[i];
		}
		return runProcess(fileAdb, device, shellCmd);
	}
	public AdbShell runProcess(final String pathAdb, final String device, final String[] cmd) {
		String[] shellCmd = createShellCmd(pathAdb, device, cmd);
		return execShellProcess(shellCmd);
	}

	public AdbShell execShellProcess(final String[] cmds) {
		Runtime run = Runtime.getRuntime();
		logAdb.debug(LOG_START_SHELL);
		Process process;
		try {
			process = run.exec(cmds);
			return new AdbShell(process);
		} catch (IOException e) {
			throw new AdbError(e);
		}
	}

	public String exec(final String[] cmds, final FormatLog formatLog) {
		Runtime run = Runtime.getRuntime();
		String res = "";

		try {
			// System.out.print("["+new
			// Date(System.currentTimeMillis()).toLocaleString()+"] : ");
			// System.out.print(new Time(System.currentTimeMillis())+" : ");
			// System.out.println("Exec " + cmd);
			// logAdb.info("Exec " + cmd);
			// logAdb.info("start process");
			currentProcess = run.exec(cmds);
			AdbConsoleThread consoleThread = new AdbConsoleThread(
					currentProcess, formatLog);

			consoleThread.start();

			currentProcess.waitFor();

			// logAdb.info("end process");
			// currentProcess.destroy();
			if (currentProcess.exitValue() != 0) {
				InputStreamReader in = new InputStreamReader(
						currentProcess.getErrorStream());

				BufferedReader buf = new BufferedReader(in);
				String lastLine = null;
				String line = buf.readLine();
				while ((line != null)) {
					if (!line.equals("")) {
						logAdb.error(formatLog.changeLine(line));

					}
					lastLine = line;
					line = buf.readLine();
				}
				throw new AdbError(lastLine);
			}
			// Thread.sleep(10);
			logAdb.debug("end process");
			if (consoleThread.isAlive()) {
				synchronized (consoleThread) {
					consoleThread.wait(CONSOLE_TIMEOUT);
				}

			}
			currentProcess = null;
			consoleThread.endConsole();
			logAdb.debug("Console End");
			res = consoleThread.getStringConsole();
			ownerAdb = ownerAdb || res.contains(CONSOLE_STARTED_ADB);

			// logAdb.info("res process");
		} catch (IOException e) {
			throw new AdbError(e);
		} catch (InterruptedException e) {
			throw new AdbError(e);
		}
		return res;
	}

	public boolean existProcess() {
		return currentProcess != null;
	}

	public boolean stopCurrentProcess() {
		logAdb.info("Stoped Curent Process");
		if (!existProcess()) {
			logAdb.error("Process not started");
			return false;
		}
		currentProcess.destroy();
		return true;

	}

	public String getNameActivity(final String packageName) {
		String activity = getNameActivityPropperty(packageName);

		if (activity == null) {
			return getFilterValue(packageName);
		}
		return activity;
	}

	public String getNameActivityPropperty(final String packageName) {
		if (propertiesActivities == null) {
			return null;
		}
		if (packageName == null) {
			return null;
		}
		return propertiesActivities.getProperty(packageName);
	}

	public String getLabelActivityPropperty(final String packageName) {
		if (propertiesActivities == null) {
			return null;
		}
		return propertiesActivities
				.getProperty(getNamePropertyLabel(packageName));
	}

	public String getLabelActivity(final String packageName) {
		return getLabelActivityPropperty(packageName);
	}

	private String getNamePropertyLabel(final String name) {
		return String.format(MASK_RPOPERTY_NAME_LABEL, name);
	}

	public void setNameActivity(final String packageName, final String nameActivity)
			throws IOException {
		if (propertiesActivities == null) {
			return;
		}
		propertiesActivities.setProperty(packageName, nameActivity);
		storeFileActivities();
	}

	private void storeFileActivities() throws FileNotFoundException,
			IOException {
		if (fileActivities != null) {
			propertiesActivities
					.store(new FileOutputStream(fileActivities), "");
		}
	}

	public void setLabelActivity(final String packageName, final String labelActivity)
			throws IOException {
		if (propertiesActivities == null) {
			return;
		}

		propertiesActivities.setProperty(getNamePropertyLabel(packageName),
				labelActivity);
		storeFileActivities();
	}

	public String getFilterValue(final String name) {
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

	public void setFilterActivities(final Properties filterActivities) {
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

	public ResourceBundle getLabelsPermissions() {
		if (labelsPermissions == null) {
			labelsPermissions = ResourceBundle
					.getBundle(NAME_RESOURCE_LABELS_PERMISSIONS);
		}
		return labelsPermissions;
	}

	public void setLocale(final Control locale) {
		labelsPermissions = ResourceBundle.getBundle(
				NAME_RESOURCE_LABELS_PERMISSIONS, locale);
	}

	public AdbPackage getInfoApk(final String pathApp) {
		logAdb.info(LOG_GET_INFO_APK.replace(MASK_FILE, pathApp));
		String[] cmds = CMD_GET_INFO_APK.split(" ");
		for (int i = 0; i < cmds.length; i++) {
			cmds[i] = cmds[i].replace(MASK_FILE, pathApp);
		}
		GetInfoApkFormatLog formatLog = new GetInfoApkFormatLog(logAdb);
		try {
			runAapt(cmds, formatLog).split("\\n");
		} catch (AdbError e) {
			logAdb.error(LOG_INSTALL_FAIL.replace(MASK_FILE, pathApp));
			e.printStackTrace();
			// throw InstallException.createInstallException(e);

		}
		logAdb.info(LOG_GET_INFO_APK_END.replace(MASK_FILE, pathApp));
		return formatLog.getPackage(this);
	}

	public String formatInfoPackage(final AdbPackage adbPackage) {
		String strInfo = DEFAULT_FORMAT_INFO_PACKAGE;
		String label = adbPackage.getLabel();
		if (label == null) {
			strInfo = strInfo.replaceAll("\\[.*" + MASK_LABEL + ".*\\]", "");
		} else {
			strInfo = strInfo.replace(MASK_LABEL, label);
			strInfo = strInfo.replaceAll("\\[", "");
			strInfo = strInfo.replaceAll("\\]", "");
		}
		strInfo = strInfo.replace(MASK_APP, adbPackage.getName());
		if (adbPackage.getFileName() != null) {
			strInfo = strInfo.replace(MASK_FILE, adbPackage.getFileName());
		}

		return String.format(strInfo, adbPackage.getName(),
				adbPackage.getLabel());
	}

	public String getLabelPermisssion(final String string) {
		ResourceBundle labelsPermissions = getLabelsPermissions();
		if (labelsPermissions.containsKey(string)) {
			return labelsPermissions.getString(string);
		} else {
			return string;
		}
	}

	public void setLogListener(final ILogListener pLogListener) {
		logAdb.setLogListener(pLogListener);
	}



}
