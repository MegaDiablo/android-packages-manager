package ru.ucoz.megadiablo.android.apm.connect;

/**
 * @author MegaDiablo
 * */
public class DeviceConnect {

	private int mPriority = 0;
	private String mAdress = "";

	public DeviceConnect() {
	}

	public DeviceConnect(final String pAdress) {
		setAdress(pAdress);
	}

	public int getPriority() {
		return mPriority;
	}

	public void setPriority(final int pPriority) {
		this.mPriority = pPriority;
	}

	public String getAdress() {
		return mAdress;
	}

	public void setAdress(final String pAdress) {
		if (pAdress != null) {
			this.mAdress = pAdress;
		} else {
			this.mAdress = "";
		}
	}

	@Override
	public String toString() {
		return mAdress;
	}
}
