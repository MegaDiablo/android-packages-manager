package ru.ucoz.megadiablo.android.apm.connect;

import java.util.ArrayList;
import java.util.List;

import ru.ucoz.megadiablo.android.apm.Core;
import ru.ucoz.megadiablo.android.apm.Settings;

/**
 * @author MegaDiablo
 * */
public class ListConnect {

	private Settings mSettings;

	public ListConnect(final Core pCore) {
		mSettings = Settings.getInstance();
	}

	public String addConnect(final String pName) {

		String name = pName;
		if (pName != null) {
			name = pName.trim();
		}

		if (name == null || name.length() == 0) {
			return null;
		}

		int maxConnect = mSettings.getConnectDeviceMaxCount();
		List<String> list = getConnects();

		List<String> remote = new ArrayList<String>();
		for (String item : list) {
			if (name.equals(item)) {
				remote.add(item);
			}
		}
		list.removeAll(remote);

		list.add(0, name);
		setListConnects(list, maxConnect);

		return name;
	}

	public void connectTo(final String name) {
		addConnect(name);
	}

	public List<String> getConnects() {
		ArrayList<String> list = new ArrayList<String>();

		int maxConnect = mSettings.getConnectDeviceMaxCount();
		for (int i = 0; i < maxConnect; i++) {

			String value = mSettings.getConnectDeviceByNumber(i);
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

	private void setListConnects(final List<String> list, final int count) {
		for (int i = 0; i < count; i++) {

			String value = "";
			if (i < list.size()) {
				value = list.get(i);
			}

			mSettings.setConnectDeviceByNumber(i, value);
		}
	}
}