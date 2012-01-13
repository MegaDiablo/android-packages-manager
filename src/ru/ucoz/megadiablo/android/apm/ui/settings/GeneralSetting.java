package ru.ucoz.megadiablo.android.apm.ui.settings;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.border.EmptyBorder;

import ru.ucoz.megadiablo.android.apm.Settings;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Window;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private JButton mButtonRestore;

	public GeneralSetting(final Window pOwner) {
		setBorder(new EmptyBorder(3, 3, 3, 3));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 103, 103, 103, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights =
				new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbcLabelGeneralSettings.insets = new Insets(0, 0, 5, 0);
		gbcLabelGeneralSettings.gridx = 0;
		gbcLabelGeneralSettings.gridy = 0;
		add(mLabelGeneralSettings, gbcLabelGeneralSettings);

		mSettingPathADB = new SettingPath(pOwner);
		mSettingPathADB.setLabel("Путь к adb :");
		GridBagConstraints gbcSettingPathADB = new GridBagConstraints();
		gbcSettingPathADB.gridwidth = 3;
		gbcSettingPathADB.insets = new Insets(0, 0, 5, 0);
		gbcSettingPathADB.fill = GridBagConstraints.BOTH;
		gbcSettingPathADB.gridx = 0;
		gbcSettingPathADB.gridy = 1;
		add(mSettingPathADB, gbcSettingPathADB);

		mSettingPathAAPT = new SettingPath((Window) null);
		mSettingPathAAPT.setLabel("Путь к aapt :");
		GridBagConstraints gbcSettingPathAAPT = new GridBagConstraints();
		gbcSettingPathAAPT.gridwidth = 3;
		gbcSettingPathAAPT.insets = new Insets(0, 0, 5, 0);
		gbcSettingPathAAPT.fill = GridBagConstraints.BOTH;
		gbcSettingPathAAPT.gridx = 0;
		gbcSettingPathAAPT.gridy = 2;
		add(mSettingPathAAPT, gbcSettingPathAAPT);

		mSettingAutostart = new JCheckBox("Автоматический запск пакетов");
		GridBagConstraints gbcCheckBoxAutostart = new GridBagConstraints();
		gbcCheckBoxAutostart.gridwidth = 3;
		gbcCheckBoxAutostart.insets = new Insets(0, 0, 5, 0);
		gbcCheckBoxAutostart.anchor = GridBagConstraints.WEST;
		gbcCheckBoxAutostart.gridx = 0;
		gbcCheckBoxAutostart.gridy = 4;
		add(mSettingAutostart, gbcCheckBoxAutostart);

		mSettingReinstall = new JCheckBox("Переустанавливать пакет");
		GridBagConstraints gbcCheckBoxReinstall = new GridBagConstraints();
		gbcCheckBoxReinstall.insets = new Insets(0, 0, 5, 0);
		gbcCheckBoxReinstall.gridwidth = 3;
		gbcCheckBoxReinstall.anchor = GridBagConstraints.WEST;
		gbcCheckBoxReinstall.gridx = 0;
		gbcCheckBoxReinstall.gridy = 5;
		add(mSettingReinstall, gbcCheckBoxReinstall);

		mButtonRestore = new JButton("Восстановить");
		mButtonRestore.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				loadSettings();
			}
		});
		GridBagConstraints gbcButtonRestore = new GridBagConstraints();
		gbcButtonRestore.insets = new Insets(0, 0, 0, 5);
		gbcButtonRestore.gridx = 1;
		gbcButtonRestore.gridy = 7;
		add(mButtonRestore, gbcButtonRestore);

		mButtonApplay = new JButton("Применить");
		mButtonApplay.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				saveSettings();
			}
		});
		GridBagConstraints gbcButtonApplay = new GridBagConstraints();
		gbcButtonApplay.gridx = 2;
		gbcButtonApplay.gridy = 7;
		add(mButtonApplay, gbcButtonApplay);

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
