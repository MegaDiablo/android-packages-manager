package ru.ucoz.megadiablo.android.apm;

/**
 * @author MegaDiablo
 * */
public class EventUpdater extends Thread {

	private Runnable mUpdater = null;

	private boolean mTerminated = false;
	private boolean mPause = false;
	private int mDelay = Consts.Default.DELAY_EVENT_UPDATER;
	private boolean mCycle = false;
	
	public EventUpdater(final Runnable updater) {
		this(updater, Consts.Default.DELAY_EVENT_UPDATER, false);
	}

	public EventUpdater(final Runnable updater, final int delay) {
		this(updater, delay, false);
	}

	public EventUpdater(final Runnable updater,
			final int delay,
			final boolean cycle) {
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
			notifyAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void setPause(final boolean pPause) {
		try {
			mPause = pPause;
				notifyAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void syncWait(final long pDelay) {
		synchronized (this) {
			try {
				if (pDelay <= 0) {
					wait(0);
				} else {
					wait(mDelay);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
