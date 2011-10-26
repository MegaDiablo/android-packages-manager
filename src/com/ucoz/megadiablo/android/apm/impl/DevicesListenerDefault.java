package com.ucoz.megadiablo.android.apm.impl;

import java.util.List;

import com.adbhelper.adb.AdbDevice;
import com.ucoz.megadiablo.android.apm.iface.DevicesListener;

/**
 * @author MegaDiablo
 * */
public class DevicesListenerDefault implements DevicesListener {

	@Override
	public void changeSelectDevice(AdbDevice pAdbDevice) {
	}

	@Override
	public void lostSelectDevice(AdbDevice pAdbDevice) {
	}

	@Override
	public void updateListDevices(List<AdbDevice> pAdbDevices) {
	}

}
