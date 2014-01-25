package ru.ucoz.megadiablo.android.apm.ui.keyboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ru.ucoz.megadiablo.android.apm.Core;

import com.adbhelper.adb.IKeyCode;
import ru.ucoz.megadiablo.android.apm.ui.settings.Settings;

/**
 * @author MegaDiablo
 * */
public class KeyBoard extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = -2856873645118217728L;

	private Core mCode = null;

	public KeyBoard(final Core pCore) {
		setResizable(false);
		mCode = pCore;

		setMinimumSize(new Dimension(320, 240));
		setTitle("Клавиатура");
		setIconImage(Settings.getInstance().getDefaultApplicationIcon());

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(3, 3, 3, 3));
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights =
				new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JTextArea textArea = new JTextArea();
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				sendKey(e.getKeyCode());
			}
		});

		JButton mButtonMenu = new JButton("Menu");
		mButtonMenu.setRequestFocusEnabled(false);
		mButtonMenu.setFocusTraversalKeysEnabled(false);
		mButtonMenu.setFocusable(false);
		mButtonMenu.setFocusPainted(false);
		mButtonMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				sendKey(KeyEvent.VK_F2);
			}
		});

		JButton mButtonHome = new JButton("Home");
		mButtonHome.setRequestFocusEnabled(false);
		mButtonHome.setFocusTraversalKeysEnabled(false);
		mButtonHome.setFocusable(false);
		mButtonHome.setFocusPainted(false);
		mButtonHome.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				sendKey(KeyEvent.VK_HOME);
			}
		});
		GridBagConstraints gbc_mButtonHome = new GridBagConstraints();
		gbc_mButtonHome.insets = new Insets(0, 0, 5, 5);
		gbc_mButtonHome.gridx = 0;
		gbc_mButtonHome.gridy = 0;
		panel.add(mButtonHome, gbc_mButtonHome);
		GridBagConstraints gbc_mButtonMenu = new GridBagConstraints();
		gbc_mButtonMenu.insets = new Insets(0, 0, 5, 5);
		gbc_mButtonMenu.gridx = 1;
		gbc_mButtonMenu.gridy = 0;
		panel.add(mButtonMenu, gbc_mButtonMenu);

		JButton btnBack = new JButton("Back");
		btnBack.setRequestFocusEnabled(false);
		btnBack.setFocusTraversalKeysEnabled(false);
		btnBack.setFocusable(false);
		btnBack.setFocusPainted(false);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				sendKey(KeyEvent.VK_ESCAPE);
			}
		});
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnBack.gridx = 2;
		gbc_btnBack.gridy = 0;
		panel.add(btnBack, gbc_btnBack);

		JButton btnSearch = new JButton("Search");
		btnSearch.setRequestFocusEnabled(false);
		btnSearch.setFocusTraversalKeysEnabled(false);
		btnSearch.setFocusable(false);
		btnSearch.setFocusPainted(false);
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				sendKey(KeyEvent.VK_F5);
			}
		});
		btnSearch.setMnemonic(KeyEvent.VK_F5);
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearch.gridx = 3;
		gbc_btnSearch.gridy = 0;
		panel.add(btnSearch, gbc_btnSearch);
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textArea.setText("Поле для ввода клавиш...");
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 4;
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 1;
		panel.add(textArea, gbc_textArea);
	}

	public void sendKey(final int pKey) {
		Integer newKey = mMapKey.get(pKey);
		if (newKey != null) {
			// System.out
			// .println(String.format("Send key $s => %s", pKey, newKey));
			mCode.sendKey(newKey);
		}
	}

	private static final Map<Integer, Integer> mMapKey =
			new HashMap<Integer, Integer>();
	static {
		mMapKey.put(KeyEvent.VK_0, IKeyCode.KEYCODE_0);
		mMapKey.put(KeyEvent.VK_1, IKeyCode.KEYCODE_1);
		mMapKey.put(KeyEvent.VK_2, IKeyCode.KEYCODE_2);
		mMapKey.put(KeyEvent.VK_3, IKeyCode.KEYCODE_3);
		mMapKey.put(KeyEvent.VK_4, IKeyCode.KEYCODE_4);
		mMapKey.put(KeyEvent.VK_5, IKeyCode.KEYCODE_5);
		mMapKey.put(KeyEvent.VK_6, IKeyCode.KEYCODE_6);
		mMapKey.put(KeyEvent.VK_7, IKeyCode.KEYCODE_7);
		mMapKey.put(KeyEvent.VK_8, IKeyCode.KEYCODE_8);
		mMapKey.put(KeyEvent.VK_9, IKeyCode.KEYCODE_9);

		mMapKey.put(KeyEvent.VK_Q, IKeyCode.KEYCODE_Q);
		mMapKey.put(KeyEvent.VK_W, IKeyCode.KEYCODE_W);
		mMapKey.put(KeyEvent.VK_E, IKeyCode.KEYCODE_E);
		mMapKey.put(KeyEvent.VK_R, IKeyCode.KEYCODE_R);
		mMapKey.put(KeyEvent.VK_T, IKeyCode.KEYCODE_T);
		mMapKey.put(KeyEvent.VK_Y, IKeyCode.KEYCODE_Y);
		mMapKey.put(KeyEvent.VK_U, IKeyCode.KEYCODE_U);
		mMapKey.put(KeyEvent.VK_I, IKeyCode.KEYCODE_I);
		mMapKey.put(KeyEvent.VK_O, IKeyCode.KEYCODE_O);
		mMapKey.put(KeyEvent.VK_P, IKeyCode.KEYCODE_P);
		mMapKey.put(KeyEvent.VK_A, IKeyCode.KEYCODE_A);
		mMapKey.put(KeyEvent.VK_S, IKeyCode.KEYCODE_S);
		mMapKey.put(KeyEvent.VK_D, IKeyCode.KEYCODE_D);
		mMapKey.put(KeyEvent.VK_F, IKeyCode.KEYCODE_F);
		mMapKey.put(KeyEvent.VK_G, IKeyCode.KEYCODE_G);
		mMapKey.put(KeyEvent.VK_H, IKeyCode.KEYCODE_H);
		mMapKey.put(KeyEvent.VK_J, IKeyCode.KEYCODE_J);
		mMapKey.put(KeyEvent.VK_K, IKeyCode.KEYCODE_K);
		mMapKey.put(KeyEvent.VK_L, IKeyCode.KEYCODE_L);
		mMapKey.put(KeyEvent.VK_Z, IKeyCode.KEYCODE_Z);
		mMapKey.put(KeyEvent.VK_X, IKeyCode.KEYCODE_X);
		mMapKey.put(KeyEvent.VK_C, IKeyCode.KEYCODE_C);
		mMapKey.put(KeyEvent.VK_V, IKeyCode.KEYCODE_V);
		mMapKey.put(KeyEvent.VK_B, IKeyCode.KEYCODE_B);
		mMapKey.put(KeyEvent.VK_N, IKeyCode.KEYCODE_N);
		mMapKey.put(KeyEvent.VK_M, IKeyCode.KEYCODE_M);

		mMapKey.put(KeyEvent.VK_SPACE, IKeyCode.KEYCODE_SPACE);
		mMapKey.put(KeyEvent.VK_COMMA, IKeyCode.KEYCODE_COMMA);
		mMapKey.put(KeyEvent.VK_BACK_SPACE, IKeyCode.KEYCODE_DEL);

		mMapKey.put(KeyEvent.VK_TAB, IKeyCode.KEYCODE_TAB);

		mMapKey.put(KeyEvent.VK_UP, IKeyCode.KEYCODE_DPAD_UP);
		mMapKey.put(KeyEvent.VK_DOWN, IKeyCode.KEYCODE_DPAD_DOWN);
		mMapKey.put(KeyEvent.VK_LEFT, IKeyCode.KEYCODE_DPAD_LEFT);
		mMapKey.put(KeyEvent.VK_RIGHT, IKeyCode.KEYCODE_DPAD_RIGHT);

		mMapKey.put(KeyEvent.VK_KP_UP, IKeyCode.KEYCODE_DPAD_UP);
		mMapKey.put(KeyEvent.VK_KP_DOWN, IKeyCode.KEYCODE_DPAD_DOWN);
		mMapKey.put(KeyEvent.VK_KP_LEFT, IKeyCode.KEYCODE_DPAD_LEFT);
		mMapKey.put(KeyEvent.VK_KP_RIGHT, IKeyCode.KEYCODE_DPAD_RIGHT);

		mMapKey.put(KeyEvent.VK_NUMPAD8, IKeyCode.KEYCODE_DPAD_UP);
		mMapKey.put(KeyEvent.VK_NUMPAD2, IKeyCode.KEYCODE_DPAD_DOWN);
		mMapKey.put(KeyEvent.VK_NUMPAD4, IKeyCode.KEYCODE_DPAD_LEFT);
		mMapKey.put(KeyEvent.VK_NUMPAD6, IKeyCode.KEYCODE_DPAD_RIGHT);
		mMapKey.put(KeyEvent.VK_NUMPAD5, IKeyCode.KEYCODE_DPAD_CENTER);

		mMapKey.put(KeyEvent.VK_ENTER, IKeyCode.KEYCODE_ENTER);

		mMapKey.put(KeyEvent.VK_HOME, IKeyCode.KEYCODE_HOME);
		mMapKey.put(KeyEvent.VK_WINDOWS, IKeyCode.KEYCODE_HOME);
		mMapKey.put(KeyEvent.VK_F2, IKeyCode.KEYCODE_MENU);
		mMapKey.put(KeyEvent.VK_CONTEXT_MENU, IKeyCode.KEYCODE_MENU);
		mMapKey.put(KeyEvent.VK_PAGE_UP, IKeyCode.KEYCODE_MENU);
		mMapKey.put(KeyEvent.VK_PAGE_DOWN, IKeyCode.KEYCODE_STAR);
		mMapKey.put(KeyEvent.VK_ESCAPE, IKeyCode.KEYCODE_BACK);
		mMapKey.put(KeyEvent.VK_DELETE, IKeyCode.KEYCODE_BACK);
		mMapKey.put(KeyEvent.VK_F3, IKeyCode.KEYCODE_CALL);
		mMapKey.put(KeyEvent.VK_F4, IKeyCode.KEYCODE_ENDCALL);
		mMapKey.put(KeyEvent.VK_F5, IKeyCode.KEYCODE_SEARCH);
		mMapKey.put(KeyEvent.VK_INSERT, IKeyCode.KEYCODE_SEARCH);
		mMapKey.put(KeyEvent.VK_F7, IKeyCode.KEYCODE_POWER);
		mMapKey.put(KeyEvent.VK_END, IKeyCode.KEYCODE_POWER);
		mMapKey.put(KeyEvent.VK_ADD, IKeyCode.KEYCODE_VOLUME_UP);
		mMapKey.put(KeyEvent.VK_SUBTRACT, IKeyCode.KEYCODE_VOLUME_DOWN);

		// mMapKey.put(KeyEvent.VK_, IKeyCode.KEYCODE_);
	}
}
