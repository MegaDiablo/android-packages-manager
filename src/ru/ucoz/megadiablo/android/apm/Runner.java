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

		final Events events = new Events();

		ListProcess process = new ListProcess();
		events.addChangeStatusListener(process);

		events.start();

		final Settings settings = Settings.getInstance();

		final AdbModule adb = new AdbModule(settings.getAdbPath(),
				Consts.settings.FILE_PROP_APP);
		adb.loadFilterActivities(Consts.settings.FILE_PROP_FILTER);

		final Core core = new Core(adb, events);

		final List<EventUpdater> listEventUpdaters = new ArrayList<EventUpdater>();

		final EventUpdater eventRefreshDevices = new EventUpdater(
				new Runnable() {
					@Override
					public void run() {
						core.refreshDevices();
					}
				}, settings.getTimeAutoRefreshDevices(), true);
		listEventUpdaters.add(eventRefreshDevices);

		final MainFrame frame = new MainFrame(core, events, listEventUpdaters);
		frame.setFilter(settings.getPackageFilterName());
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
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

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setVisible(true);
			}
		});
	}

}
