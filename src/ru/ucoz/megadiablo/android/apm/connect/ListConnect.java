package ru.ucoz.megadiablo.android.apm.connect;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ru.ucoz.megadiablo.android.apm.Core;
import ru.ucoz.megadiablo.android.apm.ui.Consts;


/**
 * @author MegaDiablo
 * */
public class ListConnect {

	private Properties mSettings;

	public ListConnect(Core pCore, Properties pSettings) {
		mSettings = pSettings;
	}

	public String addConnect(String name) {

		if (name != null) {
			name = name.trim();
		}

		if (name == null || name.length() == 0) {
			return null;
		}

		int maxConnect = getMaxConnects();
		List<String> list = getConnects();

		while (list.remove(name)) {
		}

		list.add(0, name);
		setListConnects(list, maxConnect);

		return name;
	}

	public void connectTo(String name) {
		addConnect(name);
	}

	private int getMaxConnects() {
		String strMaxConnect = mSettings.getProperty(
				Consts.settings.CONNECT_DEVICE_MAX_COUNT, "5");

		int maxConnect = 5;
		try {
			maxConnect = Integer.valueOf(strMaxConnect);
		} catch (Exception e) {
			mSettings
					.setProperty(Consts.settings.CONNECT_DEVICE_MAX_COUNT, "5");
		}

		return maxConnect;
	}

	public List<String> getConnects() {
		ArrayList<String> list = new ArrayList<String>();

		int maxConnect = getMaxConnects();
		for (int i = 0; i < maxConnect; i++) {

			String key = Consts.settings.CONNECT_DEVICE_NUMBER
					+ String.valueOf(i);

			String value = mSettings.getProperty(key, "");
			if (value != null) {
				value = value.trim();
			} else {
				value = "";
			}

			if (value.length() > 0) {
				list.add(value);
			}

		}

		return list;
	}

	private void setListConnects(List<String> list, int count) {
		for (int i = 0; i < count; i++) {

			String key = Consts.settings.CONNECT_DEVICE_NUMBER
					+ String.valueOf(i);

			String value = "";
			if (i < list.size()) {
				value = list.get(i);
			}

			mSettings.setProperty(key, value);
		}
	}
}