package ru.ucoz.megadiablo.android.apm.ui.settings;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * The Class SettingsPanel.
 * 
 * @author Alexander Gromyko
 */
public class GeneralSetting extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 171436532649840052L;
	private Settings mSettings = Settings.getInstance();
	private SettingPath mSettingPathADB;
	private SettingPath mSettingPathAAPT;
	private JCheckBox mSettingAutostart;
	private JCheckBox mSettingReinstall;
	private JButton mButtonApplay;
	private JButton mButtonReset;
	private JButton mButtonCancel;
	private JButton mButtonOk;

	public GeneralSetting(final Window pOwner) {
		setBorder(new EmptyBorder(3, 3, 3, 3));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 103, 0, 103, 72, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights =
				new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights =
				new double[] {
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						1.0,
						0.0,
						Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel mLabelGeneralSettings = new JLabel("Основные настройки");
		mLabelGeneralSettings.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbcLabelGeneralSettings = new GridBagConstraints();
		gbcLabelGeneralSettings.gridwidth = 3;
		gbcLabelGeneralSettings.insets = new Insets(0, 0, 5, 5);
		gbcLabelGeneralSettings.gridx = 0;
		gbcLabelGeneralSettings.gridy = 0;
		add(mLabelGeneralSettings, gbcLabelGeneralSettings);

		mSettingPathADB = new SettingPath(pOwner);
		mSettingPathADB.setPostfixPath("adb");
		mSettingPathADB.setLabel("Путь к adb :");
		GridBagConstraints gbcSettingPathADB = new GridBagConstraints();
		gbcSettingPathADB.gridwidth = 5;
		gbcSettingPathADB.insets = new Insets(0, 0, 5, 5);
		gbcSettingPathADB.fill = GridBagConstraints.BOTH;
		gbcSettingPathADB.gridx = 0;
		gbcSettingPathADB.gridy = 1;
		add(mSettingPathADB, gbcSettingPathADB);

		mSettingPathAAPT = new SettingPath((Window) null);
		mSettingPathAAPT.setPostfixPath("aapt");
		mSettingPathAAPT.setLabel("Путь к aapt :");
		GridBagConstraints gbcSettingPathAAPT = new GridBagConstraints();
		gbcSettingPathAAPT.gridwidth = 5;
		gbcSettingPathAAPT.insets = new Insets(0, 0, 5, 5);
		gbcSettingPathAAPT.fill = GridBagConstraints.BOTH;
		gbcSettingPathAAPT.gridx = 0;
		gbcSettingPathAAPT.gridy = 2;
		add(mSettingPathAAPT, gbcSettingPathAAPT);

		mSettingAutostart = new JCheckBox("Автоматический запск пакетов");
		GridBagConstraints gbcCheckBoxAutostart = new GridBagConstraints();
		gbcCheckBoxAutostart.gridwidth = 5;
		gbcCheckBoxAutostart.insets = new Insets(0, 0, 5, 5);
		gbcCheckBoxAutostart.anchor = GridBagConstraints.WEST;
		gbcCheckBoxAutostart.gridx = 0;
		gbcCheckBoxAutostart.gridy = 4;
		add(mSettingAutostart, gbcCheckBoxAutostart);

		mSettingReinstall = new JCheckBox("Переустанавливать пакет");
		GridBagConstraints gbcCheckBoxReinstall = new GridBagConstraints();
		gbcCheckBoxReinstall.insets = new Insets(0, 0, 5, 5);
		gbcCheckBoxReinstall.gridwidth = 5;
		gbcCheckBoxReinstall.anchor = GridBagConstraints.WEST;
		gbcCheckBoxReinstall.gridx = 0;
		gbcCheckBoxReinstall.gridy = 5;
		add(mSettingReinstall, gbcCheckBoxReinstall);

		mButtonReset = new JButton("Сбросить");
		mButtonReset.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				loadSettings();
			}
		});
		GridBagConstraints gbcButtonRestore = new GridBagConstraints();
		gbcButtonRestore.insets = new Insets(0, 0, 0, 5);
		gbcButtonRestore.gridx = 0;
		gbcButtonRestore.gridy = 7;
		add(mButtonReset, gbcButtonRestore);

		mButtonApplay = new JButton("Применить");
		mButtonApplay.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				saveSettings();
			}
		});
		GridBagConstraints gbcButtonApplay = new GridBagConstraints();
		gbcButtonApplay.insets = new Insets(0, 0, 0, 5);
		gbcButtonApplay.gridx = 1;
		gbcButtonApplay.gridy = 7;
		add(mButtonApplay, gbcButtonApplay);

		mButtonOk = new JButton("Ok");
		mButtonOk.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(final ActionEvent arg0) {
				saveSettings();
				pOwner.hide();
			}
		});
		GridBagConstraints gbc_ButtonOk = new GridBagConstraints();
		gbc_ButtonOk.fill = GridBagConstraints.HORIZONTAL;
		gbc_ButtonOk.insets = new Insets(0, 0, 0, 5);
		gbc_ButtonOk.gridx = 3;
		gbc_ButtonOk.gridy = 7;
		add(mButtonOk, gbc_ButtonOk);

		mButtonCancel = new JButton("Отмена");
		mButtonCancel.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(final ActionEvent arg0) {
				pOwner.hide();
			}
		});
		GridBagConstraints gbc_ButtonCancel = new GridBagConstraints();
		gbc_ButtonCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_ButtonCancel.gridx = 4;
		gbc_ButtonCancel.gridy = 7;
		add(mButtonCancel, gbc_ButtonCancel);

		loadSettings();
	}

	public void loadSettings() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mSettingPathADB.setPath(mSettings.getAdbPath());
				mSettingPathAAPT.setPath(mSettings.getAAPTPath());

				mSettingReinstall.setSelected(mSettings.isUseReinstall());
				mSettingAutostart.setSelected(mSettings.isAutostartPackage());
			}
		});
	}

	public void saveSettings() {
		mSettings.setAdbPath(mSettingPathADB.getPath());
		mSettings.setAAPTPath(mSettingPathAAPT.getPath());

		mSettings.setUseReinstall(mSettingReinstall.isSelected());
		mSettings.setAutostartPackage(mSettingAutostart.isSelected());
	}

}
