package ru.ucoz.megadiablo.android.apm;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import ru.ucoz.megadiablo.android.apm.ui.ListProcess;
import ru.ucoz.megadiablo.android.apm.ui.settings.Settings;

import com.adbhelper.adb.AdbModule;

/**
 * @author MegaDiablo
 * */
public final class Runner {

	public static final int EVENT_UPDATER_REFRESH_DEVICES = 0;

	/**
	 * @param args
	 * @throws java.io.IOException
	 */
	public static void main(final String[] args) throws IOException {

		extendJarPath(new File("themes"));

		final Events events = new Events();

		ListProcess process = new ListProcess();
		events.addChangeStatusListener(process);

		events.start();

		final Settings settings = Settings.getInstance();

		final AdbModule adb =
				new AdbModule(
						settings.getAdbPath(),
						settings.getAAPTPath(),
						Consts.Settings.FILE_PROP_APP);
		adb.loadFilterActivities(Consts.Settings.FILE_PROP_FILTER);
		adb.setCharset(settings.getAdbConsoleCharset());

		final Core core = new Core(adb, events);

		final List<EventUpdater> listEventUpdaters =
				new ArrayList<EventUpdater>();

		final EventUpdater eventRefreshDevices =
				new EventUpdater(
						null,
						settings.getTimeAutoRefreshDevices(),
						true);
		eventRefreshDevices.setUpdater(new Runnable() {
			@Override
			public void run() {
				if (settings.isAutorefreshListDevices()) {
					core.refreshDevices();
				}
				eventRefreshDevices.setDelay(settings
						.getTimeAutoRefreshDevices());
			}
		});
		listEventUpdaters.add(eventRefreshDevices);

		final MainFrame frame = new MainFrame(core, events, listEventUpdaters);
		frame.setFilter(settings.getPackageFilterName());
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				settings.setPackageFilterName(frame.getFilter());
				settings.save();

				eventRefreshDevices.terminated();

				for (EventUpdater item : listEventUpdaters) {
					if (item != null) {
						item.terminated();
					}
				}
				adb.finishAdb();
				events.terminated();
			}
		});

		eventRefreshDevices.start();
		if (!settings.isAutorefreshListDevices()) {
			core.refreshDevices();
			core.refreshPackages();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Метод позволяет динамически подгрузить jar файлы из указанной директории. Метод основан на хаке.
	 * Хак основан на рефликсийном доступе к protected методу addURL в системном классе URLLoader
	 */
	private static void extendJarPath(File dir) throws IOException {
		List<URL> jars = new ArrayList<URL>();
		if (dir.isDirectory() && dir.exists()) {
			File files[] = dir.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile() && file.getName().endsWith(".jar")) {
						jars.add(file.toURI().toURL());
					}
				}
			}
		}

		URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		URL urls[] = loader.getURLs();
		String urlsStr[] = new String[urls.length];
		for (int index = 0; index < urls.length; index++) {
			urlsStr[index] = urls[index].toString().toLowerCase();
		}
		urls = null;

		List<URL> jarsUnique = new ArrayList<URL>();
		for (URL jar : jars) {
			boolean unique = true;
			String testJar = jar.toString().toLowerCase();
			for (String url : urlsStr) {
				if (testJar.equals(url)) {
					unique = false;
					break;
				}
			}
			if (unique) {
				jarsUnique.add(jar);
			}
		}
		jars.clear();
		jars = null;

		Class<URLClassLoader> sysClass = URLClassLoader.class;
		try {
			Method method = sysClass.getDeclaredMethod("addURL", new Class[]{URL.class});
			method.setAccessible(true);
			for (URL jar : jarsUnique) {
				try {
					method.invoke(loader, new Object[]{jar});
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	private Runner() {
	}

}
