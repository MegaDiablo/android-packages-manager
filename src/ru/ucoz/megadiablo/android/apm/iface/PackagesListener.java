package ru.ucoz.megadiablo.android.apm.iface;

import java.util.List;

import com.adbhelper.adb.AdbPackage;

/**
 * @author MegaDiablo
 * */
public interface PackagesListener {

	public void updatePackages(List<AdbPackage> pAdbPackages);
}
