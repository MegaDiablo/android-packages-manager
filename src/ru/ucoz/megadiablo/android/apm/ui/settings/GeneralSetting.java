package ru.ucoz.megadiablo.android.apm.ui.settings;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
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

	public GeneralSetting() {
		setBorder(new EmptyBorder(3, 3, 3, 3));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{103, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		SettingPath settingPath = new SettingPath();
		settingPath.setLabel("Путь к ADB :");
		GridBagConstraints gbc_settingPath = new GridBagConstraints();
		gbc_settingPath.fill = GridBagConstraints.BOTH;
		gbc_settingPath.gridx = 0;
		gbc_settingPath.gridy = 0;
		add(settingPath, gbc_settingPath);
	}

}
