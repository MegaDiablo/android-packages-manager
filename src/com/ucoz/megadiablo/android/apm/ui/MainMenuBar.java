package com.ucoz.megadiablo.android.apm.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.ucoz.megadiablo.android.apm.AboutDialog;
import com.ucoz.megadiablo.android.apm.Core;
import com.ucoz.megadiablo.android.apm.MainFrame;
import com.ucoz.megadiablo.android.apm.ui.keyboadr.KeyBoard;

/**
 * @author ЬупфВшфидщ
 * */
public class MainMenuBar extends JMenuBar {
	/**
	 *
	 */
	private static final long serialVersionUID = -5131613473359958171L;

	private Core mCore;

	private KeyBoard mKeyBoard;

	private JCheckBoxMenuItem mCheckBoxMenuItemKeyBoard;

	public MainMenuBar(final Core pCore, final MainFrame pFrame) {
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

		JMenu mMenuDevices = new JMenu("Устройства");
		add(mMenuDevices);

		JMenuItem mMenuItemReboot = new JMenuItem("Перезагрузить");
		mMenuItemReboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(MainMenuBar.this,
						"Вы точно хотите перезагрузить устройство?",
						"Предупреждение", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					mCore.rebootDevice();
				}
			}
		});
		mMenuDevices.add(mMenuItemReboot);

		JMenu mMenuHelp = new JMenu("Помощь");
		add(mMenuHelp);

		JMenuItem mMenuItemAbout = new JMenuItem("О программе...");
		mMenuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new AboutDialog(pFrame)).setVisible(true);
			}
		});
		mMenuHelp.add(mMenuItemAbout);
	}

}
