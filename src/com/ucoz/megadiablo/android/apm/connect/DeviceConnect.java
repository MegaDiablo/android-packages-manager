package com.ucoz.megadiablo.android.apm.connect;

/**
 * @author MegaDiablo
 * */
public class DeviceConnect {

	private int mPriority = 0;
	private String mAdress = "";

	public DeviceConnect() {
	}

	public DeviceConnect(String pAdress) {
		setAdress(pAdress);
	}

	public int getPriority() {
		return mPriority;
	}

	public void setPriority(int pPriority) {
		this.mPriority = pPriority;
	}

	public String getAdress() {
		return mAdress;
	}

	public void setAdress(String pAdress) {
		if (pAdress == null) {
			pAdress = "";
		}
		this.mAdress = pAdress;
	}

	@Override
	public String toString() {
		return mAdress;
	}
}
