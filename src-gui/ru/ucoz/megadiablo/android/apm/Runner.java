package ru.ucoz.megadiablo.android.apm;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
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
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {

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

	private Runner() {
	}

}
