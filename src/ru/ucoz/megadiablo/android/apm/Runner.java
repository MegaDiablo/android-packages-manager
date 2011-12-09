package ru.ucoz.megadiablo.android.apm;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import ru.ucoz.megadiablo.android.apm.ui.ListProcess;

import com.adbhelper.adb.AdbModule;

/**
 * @author MegaDiablo
 * */
public class Runner {

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

		// final Properties properties = new Properties();
		// try {
		// properties.load(new FileInputStream(PROP));
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		// initLookAndFeel(properties.getProperty(Consts.settings.LOOK_AND_FEEL,
		// null));
		final Settings settings = Settings.getInstance();

		// initLookAndFeel(settings.getLookAndFeel());

		// final int upDeviceTime = settings.getTimeAutoRefreshDevices();

		final AdbModule adb = new AdbModule(settings.getAdbPath(),
				Consts.settings.FILE_PROP_APP);
		adb.loadFilterActivities(Consts.settings.FILE_PROP_FILTER);

		final Core core = new Core(adb, events);

		final List<EventUpdater> listEventUpdaters = new ArrayList<EventUpdater>();
		final EventUpdater eventRefreshDevices;
		{
			eventRefreshDevices = new EventUpdater(new Runnable() {
				@Override
				public void run() {
					core.refreshDevices();
				}
			}, settings.getTimeAutoRefreshDevices(), true);
			listEventUpdaters.add(eventRefreshDevices);
		}

		final MainFrame frame = new MainFrame(core, events, listEventUpdaters);
		frame.setFilter(settings.getPackageFilterName());
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				settings.setPackageFilterName(frame.getFilter());
				settings.save();

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

}
