package ru.ucoz.megadiablo.android.apm.ui.log;

import java.awt.Dimension;

import javax.swing.JDialog;

import ru.ucoz.megadiablo.android.apm.Core;
/**
 *
 * @author Vladimir Baraznovsky
 *
 */
public class LogDialog extends JDialog {

	private static final long serialVersionUID = -7228934472909425734L;

	private Core mCore;

	/**
	 * Create the dialog.
	 */
	public LogDialog(final Core pCore) {
		setResizable(false);
		mCore = pCore;
		setMinimumSize(new Dimension(320, 240));
		setTitle("Лог");

	}

}
