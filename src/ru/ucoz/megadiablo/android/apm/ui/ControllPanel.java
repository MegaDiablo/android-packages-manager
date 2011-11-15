package ru.ucoz.megadiablo.android.apm.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ru.ucoz.megadiablo.android.apm.Core;

import com.adbhelper.adb.IKeyCode;

/**
 * @author MegaDiablo
 * */
public class ControllPanel extends JPanel {
	private Core mCore = null;

	public ControllPanel(final Core pCore) {
		mCore = pCore;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JButton button = new JButton("New button");
		button.setVisible(false);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 0, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 0;
		add(button, gbc_button);

		JButton button_1 = new JButton("New button");
		button_1.setVisible(false);
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 0, 5);
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 0;
		add(button_1, gbc_button_1);

		JButton mButtonHome = new JButton("Home");
		mButtonHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mCore.sendKey(IKeyCode.KEYCODE_HOME);
			}
		});
		GridBagConstraints gbc_mButtonHome = new GridBagConstraints();
		gbc_mButtonHome.anchor = GridBagConstraints.EAST;
		gbc_mButtonHome.insets = new Insets(0, 0, 0, 5);
		gbc_mButtonHome.gridx = 3;
		gbc_mButtonHome.gridy = 0;
		add(mButtonHome, gbc_mButtonHome);

		JButton mButtonMenu = new JButton("Menu");
		mButtonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mCore.sendKey(IKeyCode.KEYCODE_MENU);
			}
		});
		GridBagConstraints gbc_mButtonMenu = new GridBagConstraints();
		gbc_mButtonMenu.insets = new Insets(0, 0, 0, 5);
		gbc_mButtonMenu.gridx = 4;
		gbc_mButtonMenu.gridy = 0;
		add(mButtonMenu, gbc_mButtonMenu);

		JButton mButtonBack = new JButton("Back");
		mButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mCore.sendKey(IKeyCode.KEYCODE_BACK);
			}
		});
		GridBagConstraints gbc_mButtonBack = new GridBagConstraints();
		gbc_mButtonBack.insets = new Insets(0, 0, 0, 5);
		gbc_mButtonBack.gridx = 5;
		gbc_mButtonBack.gridy = 0;
		add(mButtonBack, gbc_mButtonBack);

		JButton mButtonSearch = new JButton("Search");
		mButtonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mCore.sendKey(IKeyCode.KEYCODE_SEARCH);
			}
		});
		GridBagConstraints gbc_mButtonSearch = new GridBagConstraints();
		gbc_mButtonSearch.gridx = 6;
		gbc_mButtonSearch.gridy = 0;
		add(mButtonSearch, gbc_mButtonSearch);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1904581002934153238L;

}
