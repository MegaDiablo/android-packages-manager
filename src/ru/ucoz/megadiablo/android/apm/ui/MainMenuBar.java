package ru.ucoz.megadiablo.android.apm.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import ru.ucoz.megadiablo.android.apm.AboutDialog;
import ru.ucoz.megadiablo.android.apm.Consts;
import ru.ucoz.megadiablo.android.apm.Core;
import ru.ucoz.megadiablo.android.apm.EventUpdater;
import ru.ucoz.megadiablo.android.apm.MainFrame;
import ru.ucoz.megadiablo.android.apm.Runner;
import ru.ucoz.megadiablo.android.apm.ui.keyboard.KeyBoard;
import ru.ucoz.megadiablo.android.apm.ui.settings.Settings;
import ru.ucoz.megadiablo.android.apm.ui.settings.SettingsChangedListener;
import ru.ucoz.megadiablo.android.apm.ui.settings.SettingsUI;

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

	private final Settings mSettings = Settings.getInstance();

	public MainMenuBar(final Core pCore,
			final List<EventUpdater> pListEventUpdaters,
			final MainFrame pFrame) {

		mCore = pCore;

		mKeyBoard = new KeyBoard(pCore);

		JMenu mMenuFile = new JMenu("Файл");
		add(mMenuFile);

		JMenuItem mMenuItemExit = new JMenuItem("Выход");
		mMenuItemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				pFrame.close();
			}
		});

		JMenuItem mMenuItemInstallPackage = new JMenuItem("Установить пакет");
		mMenuItemInstallPackage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				chooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(final File f) {
						return f.getName().toLowerCase().endsWith(".apk")
								|| f.isDirectory();
					}

					@Override
					public String getDescription() {
						return "Android applications (*.apk)";
					}
				});

				int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File[] file = chooser.getSelectedFiles();
					mCore.install(file);
				}
			}
		});
		mMenuFile.add(mMenuItemInstallPackage);

		JSeparator separatorFIleExit = new JSeparator();
		mMenuFile.add(separatorFIleExit);
		mMenuFile.add(mMenuItemExit);

		JMenu mMenuView = new JMenu("Вид");
		add(mMenuView);

		mCheckBoxMenuItemKeyBoard = new JCheckBoxMenuItem("Клавиатура");
		mCheckBoxMenuItemKeyBoard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				mKeyBoard.setVisible(mCheckBoxMenuItemKeyBoard.isSelected());
			}
		});
		mMenuView.add(mCheckBoxMenuItemKeyBoard);

		JCheckBoxMenuItem mCheckBoxMenuItemSystemPackages =
				new JCheckBoxMenuItem("Системыне пакеты");
		mCheckBoxMenuItemSystemPackages.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				JCheckBoxMenuItem item = (JCheckBoxMenuItem) (arg0.getSource());
				mSettings.setVisibleSystemPackages(item.isSelected());
				mCore.refreshPackages();
			}
		});
		mCheckBoxMenuItemSystemPackages.setSelected(mSettings
				.isVisibleSystemPackages());
		mMenuView.add(mCheckBoxMenuItemSystemPackages);

		JMenuLookAndFeel mMenuLookAndFeel = new JMenuLookAndFeel(pCore);
		mMenuView.add(mMenuLookAndFeel);

		JMenu mMenuDevices = new JMenu("Устройства");
		add(mMenuDevices);

		JMenuItem mMenuItemReboot = new JMenuItem("Перезагрузить");
		mMenuItemReboot.setToolTipText("Перезагружает текущее устройство");
		mMenuItemReboot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (showWarning("Вы точно хотите перезагрузить устройство?")) {
					mCore.rebootDevice();
				}
			}
		});

		mMenuAdbConnect = new JMenu("Подключить");
		mMenuDevices.add(mMenuAdbConnect);

		mMenuItemAddNetworkDevice = new JMenuItem("Новое соединение");
		mMenuItemAddNetworkDevice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				AddConnect connect = new AddConnect();
				connect.setVisible(true);
				if (connect.getResult() == JOptionPane.OK_OPTION) {
					String name =
							mCore.getListConnects().addConnect(
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

				JMenuItem mMenuItemClearFolderTemp = new JMenuItem("Очистить папку temp");
				mMenuDevices.add(mMenuItemClearFolderTemp);
				mMenuItemClearFolderTemp.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent e) {
						mCore.clearFolderTemp();
					}
				});

		JSeparator separatorRebootDevice = new JSeparator();
		mMenuDevices.add(separatorRebootDevice);
		mMenuDevices.add(mMenuItemReboot);

		JMenu mMenuADB = new JMenu("ADB");
		add(mMenuADB);

		JMenuItem mMenuItemAdbStart = new JMenuItem("Запустить");
		mMenuItemAdbStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
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
			@Override
			public void actionPerformed(final ActionEvent e) {
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
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (showWarning("Вы точно хотите остановить ADB?")) {
					pListEventUpdaters
							.get(Runner.EVENT_UPDATER_REFRESH_DEVICES)
							.setPause(true);
					mCore.stopAdb();
				}
			}
		});
		mMenuADB.add(mMenuItemAdbStop);

		JMenu mMenuSettings = new JMenu("Настройки");
		add(mMenuSettings);

		mCheckBoxMenuItemAutostartPackage =
				new JCheckBoxMenuItem("Автозапуск пакета");
		mCheckBoxMenuItemAutostartPackage
				.setToolTipText("Запускает пакет после установки");
		mCheckBoxMenuItemAutostartPackage
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(final ActionEvent arg0) {
						JCheckBoxMenuItem item =
								(JCheckBoxMenuItem) (arg0.getSource());
						mSettings.setAutostartPackage(item.isSelected());
					}
				});
		mMenuSettings.add(mCheckBoxMenuItemAutostartPackage);

		mCheckBoxMenuItemUseReinstall =
				new JCheckBoxMenuItem("Переустановка пакета");
		mCheckBoxMenuItemUseReinstall
				.setToolTipText("Установка происходит с ключем переустановки");
		mCheckBoxMenuItemUseReinstall.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				JCheckBoxMenuItem item = (JCheckBoxMenuItem) (arg0.getSource());
				mSettings.setUseReinstall(item.isSelected());
			}
		});
		mMenuSettings.add(mCheckBoxMenuItemUseReinstall);

		JSeparator mSeparatorSettings = new JSeparator();
		mMenuSettings.add(mSeparatorSettings);

		JMenuItem mMenuItemSettingsAdvanced = new JMenuItem("Подробно...");
		mMenuItemSettingsAdvanced.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				(new SettingsUI()).setVisible(true);
			}
		});
		mMenuSettings.add(mMenuItemSettingsAdvanced);

		JMenu mMenuHelp = new JMenu("Помощь");
		add(mMenuHelp);

		JMenuItem mMenuItemAbout = new JMenuItem("О программе...");
		mMenuItemAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				(new AboutDialog(null)).setVisible(true);
			}
		});
		mMenuHelp.add(mMenuItemAbout);

		mSettings.addChangedListener(mSettingsChangedListener);
		loadSettings();

		refreshConnects();
	}

	private boolean showWarning(final String text) {
		int result =
				JOptionPane.showConfirmDialog(
						MainMenuBar.this,
						text,
						"Предупреждение",
						JOptionPane.YES_NO_OPTION,
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
					@Override
					public void actionPerformed(final ActionEvent e) {
						mCore.connectNetworkDevice(connect);
						mCore.getListConnects().connectTo(connect);
						refreshConnects();
					}
				});
				mMenuAdbConnect.add(item, 0);

			}
		}
	}

	private void loadSettings() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mCheckBoxMenuItemUseReinstall.setSelected(mSettings
						.isUseReinstall());
				mCheckBoxMenuItemAutostartPackage.setSelected(mSettings
						.isAutostartPackage());
			}
		});
	}

	private final SettingsChangedListener mSettingsChangedListener =
			new SettingsChangedListener() {
				@Override
				public void changedSettings(final String pName) {
					if (Consts.Settings.SETTINGS_PACKAGE_AUTOSTART
							.equals(pName)
							|| Consts.Settings.SETTINGS_PACKAGE_USE_REINSTALL
									.equals(pName)) {

						loadSettings();
					}
				}
			};

	private JCheckBoxMenuItem mCheckBoxMenuItemAutostartPackage;

	private JCheckBoxMenuItem mCheckBoxMenuItemUseReinstall;
}
