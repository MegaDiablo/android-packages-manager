package ru.ucoz.megadiablo.android.apm.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import ru.ucoz.megadiablo.android.apm.AboutDialog;
import ru.ucoz.megadiablo.android.apm.Core;
import ru.ucoz.megadiablo.android.apm.EventUpdater;
import ru.ucoz.megadiablo.android.apm.MainFrame;
import ru.ucoz.megadiablo.android.apm.Runner;
import ru.ucoz.megadiablo.android.apm.ui.keyboard.KeyBoard;

/**
 * @author MegaDiablo
 * */
public class MainMenuBar extends JMenuBar {
	/**
	 *
	 */
	private static final long serialVersionUID = -5131613473359958171L;

	private Core mCore;

	private KeyBoard mKeyBoard;

	private JCheckBoxMenuItem mCheckBoxMenuItemKeyBoard;

	private JMenu mMenuAdbConnect;

	private JSeparator mSeparatorConnects;

	private JMenuItem mMenuItemAddNetworkDevice;

	private JMenuItem mMenuItemEmpty;

	public MainMenuBar(final Core pCore,
			final List<EventUpdater> pListEventUpdaters, final MainFrame pFrame) {

		mCore = pCore;

		mKeyBoard = new KeyBoard(pCore);

		JMenu mMenuFile = new JMenu("Файл");
		add(mMenuFile);

		JMenuItem mMenuItemExit = new JMenuItem("Выход");
		mMenuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pFrame.close();
			}
		});
		mMenuFile.add(mMenuItemExit);

		JMenu mMenuView = new JMenu("Вид");
		add(mMenuView);

		mCheckBoxMenuItemKeyBoard = new JCheckBoxMenuItem("Клавиатура");
		mCheckBoxMenuItemKeyBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mKeyBoard.setVisible(mCheckBoxMenuItemKeyBoard.isSelected());
			}
		});
		mMenuView.add(mCheckBoxMenuItemKeyBoard);

		JMenuLookAndFeel mMenuLookAndFeel = new JMenuLookAndFeel(pCore);
		mMenuView.add(mMenuLookAndFeel);

		JMenu mMenuDevices = new JMenu("Устройства");
		add(mMenuDevices);

		JMenuItem mMenuItemReboot = new JMenuItem("Перезагрузить");
		mMenuItemReboot.setToolTipText("Перезагружает текущее устройство");
		mMenuItemReboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showWarning("Вы точно хотите перезагрузить устройство?")) {
					mCore.rebootDevice();
				}
			}
		});

		mMenuAdbConnect = new JMenu("Подключить");
		mMenuDevices.add(mMenuAdbConnect);

		mMenuItemAddNetworkDevice = new JMenuItem("Новое соединение");
		mMenuItemAddNetworkDevice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddConnect connect = new AddConnect();
				connect.setVisible(true);
				if (connect.getResult() == JOptionPane.OK_OPTION) {
					String name = mCore.getListConnects().addConnect(
							connect.getAdress());
					if (name != null) {
						mCore.connectNetworkDevice(name);
						refreshConnects();
					}
				}
			}
		});

		mMenuItemEmpty = new JMenuItem("Нет соединений");
		mMenuItemEmpty.setContentAreaFilled(false);
		mMenuItemEmpty.setRequestFocusEnabled(false);
		mMenuItemEmpty.setEnabled(false);
		mMenuAdbConnect.add(mMenuItemEmpty);

		mSeparatorConnects = new JSeparator();
		mMenuAdbConnect.add(mSeparatorConnects);
		mMenuAdbConnect.add(mMenuItemAddNetworkDevice);
		mMenuDevices.add(mMenuItemReboot);

		JMenu mMenuADB = new JMenu("ADB");
		add(mMenuADB);

		JMenuItem mMenuItemAdbStart = new JMenuItem("Запустить");
		mMenuItemAdbStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showWarning("Вы точно хотите запустить ADB?")) {
					pListEventUpdaters
							.get(Runner.EVENT_UPDATER_REFRESH_DEVICES)
							.setPause(false);
					mCore.startAdb();
				}
			}
		});
		mMenuADB.add(mMenuItemAdbStart);

		JMenuItem mMenuItemAdbReboot = new JMenuItem("Перезагрузить");
		mMenuItemAdbReboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showWarning("Вы точно хотите перезапустить ADB?")) {
					pListEventUpdaters
							.get(Runner.EVENT_UPDATER_REFRESH_DEVICES)
							.setPause(false);
					mCore.restartAdb();
				}
			}
		});
		mMenuADB.add(mMenuItemAdbReboot);

		JMenuItem mMenuItemAdbStop = new JMenuItem("Остановить");
		mMenuItemAdbStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showWarning("Вы точно хотите остановить ADB?")) {
					pListEventUpdaters
							.get(Runner.EVENT_UPDATER_REFRESH_DEVICES)
							.setPause(true);
					mCore.stopAdb();
				}
			}
		});
		mMenuADB.add(mMenuItemAdbStop);

		JMenu mMenuHelp = new JMenu("Помощь");
		add(mMenuHelp);

		JMenuItem mMenuItemAbout = new JMenuItem("О программе...");
		mMenuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new AboutDialog(null)).setVisible(true);
			}
		});
		mMenuHelp.add(mMenuItemAbout);

		refreshConnects();
	}

	private boolean showWarning(String text) {
		int result = JOptionPane.showConfirmDialog(MainMenuBar.this, text,
				"Предупреждение", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

	private void refreshConnects() {
		if (mCore == null) {
			return;
		}

		List<String> list = mCore.getListConnects().getConnects();

		Component[] components = mMenuAdbConnect.getMenuComponents();
		for (int i = 0; i < components.length; i++) {
			Component component = components[i];
			if (component != mMenuItemAddNetworkDevice
					&& component != mSeparatorConnects
					&& component != mMenuItemEmpty) {

				mMenuAdbConnect.remove(component);
			}
		}

		if (list.size() > 0) {
			mMenuItemEmpty.setVisible(false);
		} else {
			mMenuItemEmpty.setVisible(true);
		}

		for (int i = list.size() - 1; i >= 0; i--) {

			final String connect = list.get(i);
			String value = connect;
			if ("".equals(value)) {
				value = null;
			}

			if (value == null) {

				JMenuItem item = new JMenuItem("< Пусто >");
				item.setEnabled(false);
				mMenuAdbConnect.add(item);

			} else {

				JMenuItem item = new JMenuItem(connect);
				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mCore.connectNetworkDevice(connect);
						mCore.getListConnects().connectTo(connect);
						refreshConnects();
					}
				});
				mMenuAdbConnect.add(item, 0);

			}
		}
	}
}
