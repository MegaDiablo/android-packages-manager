package ru.ucoz.megadiablo.android.apm;

/**
 * @author MegaDiablo
 * */
public class EventUpdater extends Thread {

	private Runnable mUpdater = null;

	private boolean mTerminated = false;
	private boolean mPause = false;
	private int mDelay = 5000;
	private boolean mCycle = false;

	public EventUpdater(Runnable updater) {
		this(updater, 5000, false);
	}

	public EventUpdater(Runnable updater, int delay) {
		this(updater, delay, false);
	}

	public EventUpdater(Runnable updater, int delay, boolean cycle) {
		super("EventUpdater");

		mUpdater = updater;
		mDelay = Math.abs(delay);
		mCycle = cycle;
	}

	@Override
	public void run() {
		if (mUpdater == null) {
			return;
		}

		boolean prov;

		while (!mTerminated) {

			try {
				mUpdater.run();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!mCycle) {
				break;
			}

			prov = true;
			while (prov) {
				syncWait(mDelay);
				if (mPause) {
					while (mPause && !mTerminated) {
						syncWait(-1);
					}
					if (!mTerminated) {
						continue;
					}
				}
				prov = false;
			}
		}
	}

	public synchronized void terminated() {
		try {
			mTerminated = true;
			synchronized (this) {
				notifyAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void setPause(boolean pPause) {
		try {
			mPause = pPause;
			synchronized (this) {
				notifyAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void syncWait(int pDelay) {
		synchronized (this) {
			try {
				if (pDelay <= 0) {
					wait();
				} else {
					wait(mDelay);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
