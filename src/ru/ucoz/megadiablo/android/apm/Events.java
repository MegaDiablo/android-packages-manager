package ru.ucoz.megadiablo.android.apm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MegaDiablo
 * */
public class Events extends Thread {

	private ArrayList<IChangeStatus> mListChangeStatus = new ArrayList<Events.IChangeStatus>();

	private LinkedList<IEvent> mList = new LinkedList<IEvent>();
	private boolean mTerminate = false;

	private boolean mLog = true;

	@Override
	public void run() {
		fireChangeStatus(IChangeStatus.START);
		fireUpdateStatus(mList);
		while (!mTerminate) {
			try {
				if (mList.isEmpty()) {
					synchronized (this) {
						fireChangeStatus(IChangeStatus.WAIT);
						wait();
						fireChangeStatus(IChangeStatus.WAKE);
						fireUpdateStatus(mList);
					}
				} else {
					IEvent item = mList.getFirst();
					logEvent(item);

					try {
						item.run();
					} catch (Exception e) {
						e.printStackTrace();
					}

					mList.removeFirst();
					fireUpdateStatus(mList);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		fireChangeStatus(IChangeStatus.STOP);
	}

	public synchronized void terminated() {
		mTerminate = true;
	}

	public void add(String pName, Runnable pRunnable) {
		add(pName, "", pRunnable);
	}

	public void add(String pName, String pDescription, Runnable pRunnable) {

		add(ImplEvent.createEvent(pName, pDescription, pRunnable));
	}

	public synchronized void add(IEvent pEvent) {
		if (pEvent != null) {
			mList.addLast(pEvent);
			wake();
			fireUpdateStatus(mList);
		}
	}

	public synchronized void addChangeStatusListener(
			IChangeStatus pChangeStatusListener) {
		if (pChangeStatusListener != null) {
			mListChangeStatus.add(pChangeStatusListener);
		}
	}

	public synchronized void clearList() {
		mList.clear();
		wake();
		fireUpdateStatus(mList);
	}

	private void logEvent(IEvent pEvent) {
		if (mLog) {
			sysLog(String.format("Name : %s", pEvent.getName()));
			sysLog(String.format("Description : %s", pEvent.getDescription()));
		}
	}

	private void sysLog(String text) {
		// System.out.println(text);
		String enc = System.getProperty("console.encoding");
		String enc2 = System.getProperty("file.encoding");

		if (enc == null) {
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				enc = "cp866";
			} else {
				enc = System.getProperty("file.encoding");
			}
		}

		try {
			System.out.println(new String(text.getBytes(enc), enc2));
		} catch (UnsupportedEncodingException e) {
			System.out.println(text);
		}
	}

	private synchronized void fireChangeStatus(final int pStatus) {
		for (IChangeStatus item : mListChangeStatus) {
			item.changeStatus(pStatus);
		}
	}

	private synchronized void fireUpdateStatus(final List<IEvent> pEvents) {
		for (IChangeStatus item : mListChangeStatus) {
			item.updateList(pEvents);
		}
	}

	private void wake() {
		try {
			synchronized (this) {
				notifyAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author MegaDiablo
	 * */
	public interface IChangeStatus {

		public static final int START = 0;
		public static final int WAKE = 1;
		public static final int WAIT = 2;
		public static final int STOP = 3;

		public void changeStatus(int pStatus);

		public void updateList(List<IEvent> pEvents);
	}

}
