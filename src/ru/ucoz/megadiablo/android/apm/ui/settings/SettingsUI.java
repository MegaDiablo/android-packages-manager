package ru.ucoz.megadiablo.android.apm.ui.settings;

import java.awt.Dimension;

import javax.swing.JDialog;
import java.awt.BorderLayout;

/**
 * @author MegaDiablo
 * */
public class SettingsUI extends JDialog {

	/**
	 *
	 */
	private static final long serialVersionUID = -2856873645118217728L;

	public SettingsUI() {
		setTitle("Настройки");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setModal(true);

		setMinimumSize(new Dimension(530, 320));
		setResizable(false);
		setAlwaysOnTop(true);
		getContentPane().setLayout(new BorderLayout(0, 0));

		GeneralSetting generalSetting = new GeneralSetting();
		getContentPane().add(generalSetting, BorderLayout.CENTER);

		setLocationRelativeTo(null);
	}

}
