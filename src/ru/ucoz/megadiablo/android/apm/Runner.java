package ru.ucoz.megadiablo.android.apm;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.SwingUtilities;

import ru.ucoz.megadiablo.android.apm.ui.Consts;
import ru.ucoz.megadiablo.android.apm.ui.EnumPLAF;
import ru.ucoz.megadiablo.android.apm.ui.ListProcess;

import com.adbhelper.adb.AdbModule;

/**
 * @author MegaDiablo
 * */
public class Runner {

	public static EnumPLAF LOOK_AND_FEEL;

	private static final String PROP = "apm.prop";
	private static final String PROP_APP = "app.prop";
	private static final String PROP_FILTER = "filter.prop";
	private static final String PROPERTY_FILTER_TEXT = Consts.settings.FILTER_TEXT;
	private static final String PROPERTY_PATH_ADB = Consts.settings.PATH_ADB;

	private static final String PROPERTY_AUTO_REFRESH_DEVICES = Consts.settings.DEVICE_AUTO_REFRESH;

	public static int EVENT_UPDATER_REFRESH_DEVICES = 0;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// {
		// Enumeration<Object> k = System.getProperties().keys();
		// while (k.hasMoreElements()) {
		// String o = (String) k.nextElement();
		// System.out.println(o + " = "
		// + System.getProperties().getProperty(o));
		// }
		// }

		final Events events = new Events();
		{
			ListProcess process = new ListProcess();
			events.addChangeStatusListener(process);
		}
		events.start();

		final Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(PROP));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		initLookAndFeel(properties.getProperty(Consts.settings.LOOK_AND_FEEL,
				null));

		final int upDeviceTime = Integer.parseInt(properties.getProperty(
				PROPERTY_AUTO_REFRESH_DEVICES, "5000"));
		properties.put(PROPERTY_AUTO_REFRESH_DEVICES,
				String.valueOf(upDeviceTime));

		final AdbModule adb = new AdbModule(properties.getProperty(
				PROPERTY_PATH_ADB, "adb"), PROP_APP);
		adb.loadFilterActivities(PROP_FILTER);

		final Core core = new Core(adb, events, properties);

		final List<EventUpdater> listEventUpdaters = new ArrayList<EventUpdater>();
		final EventUpdater eventRefreshDevices;
		{
			eventRefreshDevices = new EventUpdater(new Runnable() {
				@Override
				public void run() {
					core.refreshDevices();
				}
			}, upDeviceTime, true);
			listEventUpdaters.add(eventRefreshDevices);
		}

		final MainFrame frame = new MainFrame(core, events, listEventUpdaters);
		frame.setFilter(properties.getProperty(PROPERTY_FILTER_TEXT, ""));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				properties.put(PROPERTY_FILTER_TEXT, frame.getFilter());
				try {
					properties.store(new FileOutputStream(PROP), "");
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				eventRefreshDevices.terminated();

				// events.terminated();
				for (EventUpdater item : listEventUpdaters) {
					if (item != null) {
						item.terminated();
					}
				}
				adb.finishAdb();
			}
		});

		{
			eventRefreshDevices.start();
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setVisible(true);
			}
		});
	}

	private static void initLookAndFeel(final String pName) {
		LOOK_AND_FEEL = null;

		if (pName != null) {
			LOOK_AND_FEEL = EnumPLAF.findByName(pName);
		}

		if (LOOK_AND_FEEL == null) {
			LOOK_AND_FEEL = EnumPLAF.AERO;
		}
	}
}
