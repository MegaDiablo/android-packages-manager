package ru.ucoz.megadiablo.android.apm;

/**
 * Класс служит ключем для синхронизации потоков.
 * 
 * @author Alexander Gromyko
 * */
public class Token {

	private int mActive = 0;

	public int getActive() {
		return mActive;
	}

	public void setActive(final int pActive) {
		mActive = pActive;
	}

	public void takeToken() throws InterruptedException {
		mActive++;
	}

	public void freeToken() {
		mActive--;
		if (mActive < 0) {
			mActive = 0;
		}
	}
	
	public boolean isActive() {
		return mActive > 0;
	}
}
