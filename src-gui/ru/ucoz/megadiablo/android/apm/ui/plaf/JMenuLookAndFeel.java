package ru.ucoz.megadiablo.android.apm.ui.plaf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import ru.ucoz.megadiablo.android.apm.Core;
import ru.ucoz.megadiablo.android.apm.ui.settings.Settings;

/**
 * @author MegaDiablo
 */
public class JMenuLookAndFeel extends JMenu {

	/**
	 *
	 */
	private static final long serialVersionUID = -2843337575356128673L;

	private ButtonGroup mGroup;
	private final Core mCore;

	public JMenuLookAndFeel(final Core pCore) {
		setText("Оформление");

		mCore = pCore;
		mGroup = new ButtonGroup();

		String os = "???";

		String osName = System.getProperty("os.name");
		osName = osName == null ? "" : osName.toLowerCase();
		if (osName.startsWith("windows")) {
			os = "win";
		} else if (osName.startsWith("linux")) {
			os = "nix";
		}


		Settings settings = Settings.getInstance();
		ThemeManager manager = settings.getThemeManager();
		Theme[] themes = manager.getThemes();

		String currentTheme = settings.getLookAndFeel();
		if (currentTheme == null) {
			currentTheme = "";
		}
		addItem("Системное", null, "".equals(currentTheme));
		for (Theme theme : themes) {
			addItem(theme.getFullName(), theme, currentTheme.equals(theme.getKey()));
		}
	}

	private void addItem(final String pText, final Theme pTheme, final boolean pSelected) {

		JRadioButtonMenuItem mMenuItem = new JRadioButtonMenuItem(pText);
		mGroup.add(mMenuItem);
		mMenuItem.setSelected(pSelected);
		add(mMenuItem);
		mMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mCore.setLookAndFeel(pTheme);

				JOptionPane
					.showMessageDialog(
						JMenuLookAndFeel.this,
						"Изменения вступят в силу после перезагрузки приложенияю.",
						"Предупреждение",
						JOptionPane.WARNING_MESSAGE);
			}
		});
	}
}
