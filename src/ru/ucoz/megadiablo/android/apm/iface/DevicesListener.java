package ru.ucoz.megadiablo.android.apm.iface;

import java.util.List;

import com.adbhelper.adb.AdbDevice;

/**
 * @author MegaDiablo
 * */
public interface DevicesListener {

	void changeSelectDevice(AdbDevice pAdbDevice);

	void lostSelectDevice(AdbDevice pAdbDevice);

	void updateListDevices(List<AdbDevice> pAdbDevices);

}
