package ru.ucoz.megadiablo.android.apm.ui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author MegaDiablo
 * */
public final class UserInterfaceUtils {

	private UserInterfaceUtils() {
	}

	public static void setLookAndFeel(final EnumPLAF plaf) {
		if (plaf == null) {
			JFrame.setDefaultLookAndFeelDecorated(false);
			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			plaf.setThemeDefault();
			JFrame.setDefaultLookAndFeelDecorated(true);
			try {
				UIManager.setLookAndFeel(plaf.getPLookAndFeel());
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}
	}
}
