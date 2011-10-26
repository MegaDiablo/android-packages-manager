package com.ucoz.megadiablo.android.apm.iface;

import java.util.List;

import com.adbhelper.adb.AdbDevice;

/**
 * @author MegaDiablo
 * */
public interface DevicesListener {

	public void changeSelectDevice(AdbDevice pAdbDevice);

	public void lostSelectDevice(AdbDevice pAdbDevice);

	public void updateListDevices(List<AdbDevice> pAdbDevices);

}
