package ru.ucoz.megadiablo.android.apm.ui.settings;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;

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
		setModalityType(ModalityType.DOCUMENT_MODAL);
		setModal(true);

		setMinimumSize(new Dimension(530, 320));
		setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		GeneralSetting generalSetting = new GeneralSetting(this);
		getContentPane().add(generalSetting, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
	}

}
