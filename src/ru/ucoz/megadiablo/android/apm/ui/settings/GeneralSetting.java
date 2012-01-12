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
	private SettingPath mSettingPath;

	public GeneralSetting(final Window pOwner) {
		setBorder(new EmptyBorder(3, 3, 3, 3));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 103, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel mLabelGeneralSettings = new JLabel("Основные настройки");
		mLabelGeneralSettings.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbcLabelGeneralSettings = new GridBagConstraints();
		gbcLabelGeneralSettings.insets = new Insets(0, 0, 5, 0);
		gbcLabelGeneralSettings.gridx = 0;
		gbcLabelGeneralSettings.gridy = 0;
		add(mLabelGeneralSettings, gbcLabelGeneralSettings);

		mSettingPath = new SettingPath(pOwner);
		mSettingPath.setLabel("Путь к ADB :");
		GridBagConstraints gbcSettingPath = new GridBagConstraints();
		gbcSettingPath.fill = GridBagConstraints.BOTH;
		gbcSettingPath.gridx = 0;
		gbcSettingPath.gridy = 1;
		add(mSettingPath, gbcSettingPath);

		loadSettings();
	}

	public void loadSettings() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mSettingPath.setPath(mSettings.getAdbPath());
			}
		});
	}

	public void saveSettings() {
		mSettings.setAdbPath(mSettingPath.getPath());
	}

}
