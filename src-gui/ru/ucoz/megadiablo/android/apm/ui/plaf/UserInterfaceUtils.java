package ru.ucoz.megadiablo.android.apm.ui.plaf;

import javax.swing.*;

/**
 * @author MegaDiablo
 */
public final class UserInterfaceUtils {

	private UserInterfaceUtils() {
	}

	public static void setLookAndFeel(final Theme pTheme) {
		if (pTheme == null) {
			setSystemTheme();
		} else {
//			plaf.setThemeDefault();
			JFrame.setDefaultLookAndFeelDecorated(true);
			try {
				LookAndFeel laf = pTheme.getParent().getLookAndFeel();
				if (laf != null) {
					UIManager.setLookAndFeel(laf);
				} else {
					setSystemTheme();
				}
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
				setSystemTheme();
			}
		}
	}
	
	private static void setSystemTheme() {
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
	}
}
