package ru.ucoz.megadiablo.android.apm;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MegaDiablo
 * */
public class Events extends Thread {

	private ArrayList<IChangeStatus> mListChangeStatus =
			new ArrayList<Events.IChangeStatus>();

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
					fireChangeStatus(IChangeStatus.WAIT);
					wait();
					fireChangeStatus(IChangeStatus.WAKE);
					fireUpdateStatus(mList);
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

	public void add(final String pName, final Runnable pRunnable) {
		add(EnumEvents.NONE, pName, "", pRunnable);
	}

	public void add(final String pName,
			final Runnable pRunnable,
			final boolean pLastDublicate) {

		add(EnumEvents.NONE, pName, "", pRunnable, pLastDublicate);
	}

	public void add(final EnumEvents pEnumEvents,
			final String pName,
			final String pDescription,
			final Runnable pRunnable) {

		add(pEnumEvents.getType(), pName, "", pRunnable);
	}

	public void add(final EnumEvents pEnumEvents,
			final String pName,
			final String pDescription,
			final Runnable pRunnable,
			final boolean pLastDublicate) {

		add(pEnumEvents.getType(), pName, "", pRunnable, pLastDublicate);
	}

	public void add(final int pType,
			final String pName,
			final String pDescription,
			final Runnable pRunnable) {

		add(ImplEvent.createEvent(pType, pName, pDescription, pRunnable));
	}

	public void add(final int pType,
			final String pName,
			final String pDescription,
			final Runnable pRunnable,
			final boolean pLastDublicate) {

		add(
				ImplEvent.createEvent(pType, pName, pDescription, pRunnable),
				pLastDublicate);
	}

	public synchronized void add(final IEvent pEvent) {
		add(pEvent, true);
	}

	/**
	 * @param pEvent
	 *            событие которое добавляем в очередь
	 * @param pLastDublicate
	 *            дублировать последнее событие если они одного типа. true -
	 *            всеравно добавлять в очередь.
	 * */
	public synchronized void add(final IEvent pEvent,
			final boolean pLastDublicate) {

		if (pEvent != null) {
			if (!mList.isEmpty()) {
				IEvent event = mList.getLast();
				if (pEvent.equals(event) && !pLastDublicate) {
					return;
				}
			}
			mList.addLast(pEvent);
			wake();
			fireUpdateStatus(mList);
		}
	}

	public synchronized void addChangeStatusListener(final IChangeStatus pChangeStatusListener) {
		if (pChangeStatusListener != null) {
			mListChangeStatus.add(pChangeStatusListener);
		}
	}

	public synchronized void clearList() {
		mList.clear();
		wake();
		fireUpdateStatus(mList);
	}

	private void logEvent(final IEvent pEvent) {
		if (mLog) {
			sysLog(String.format("Name : %s", pEvent.getName()));
			sysLog(String.format("Description : %s", pEvent.getDescription()));
		}
	}

	private void sysLog(final String text) {
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
			notifyAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author MegaDiablo
	 * */
	public interface IChangeStatus {

		int START = 0;
		int WAKE = 1;
		int WAIT = 2;
		int STOP = 3;

		void changeStatus(int pStatus);

		void updateList(List<IEvent> pEvents);
	}

}
