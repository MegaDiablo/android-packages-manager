package com.ucoz.megadiablo.android.apm;

public class EventUpdater extends Thread {

	private Runnable mUpdater = null;

	private boolean mTerminated = false;
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

		while (!mTerminated) {

			try {
				mUpdater.run();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (!mCycle) {
				break;
			}

			synchronized (this) {
				try {
					wait(mDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
}
