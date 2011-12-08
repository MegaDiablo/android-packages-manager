package ru.ucoz.megadiablo.android.apm;

/**
 * @author MegaDiablo
 * */
public class ImplEvent implements IEvent {

	private Runnable mRunnable = null;
	private String mName = null;
	private String mDecription = null;

	@Override
	public void run() {
		if (mRunnable != null) {
			mRunnable.run();
		}
	}

	@Override
	public String getName() {
		return mName;
	}

	@Override
	public String getDescription() {
		return mDecription;
	}

	public static ImplEvent createEvent(String pName, String pDescription,
			Runnable pRunnable) {

		ImplEvent event = new ImplEvent();

		event.mName = pName;
		event.mDecription = pDescription;
		event.mRunnable = pRunnable;

		return event;
	}

	@Override
	public String toString() {
		if (mName == null) {
			mName = super.toString();
		}
		return mName;
	}
}
