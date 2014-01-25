package ru.ucoz.megadiablo.android.apm.ui;

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
 * */
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
		EnumPLAF[] themes = EnumPLAF.values();

		String os = "???";
		
		String osName = System.getProperty("os.name");
		osName = osName == null ? "" : osName.toLowerCase();
		if (osName.startsWith("windows")) {
			os = "win";
		} else if (osName.startsWith("linux")) {
			os = "nix";
		}

		
		for (EnumPLAF theme : themes) {
			if (theme.getPlatform() == null || theme.getPlatform().equals(os)) {
				addItem(theme.getText(), theme);
			}
		}
	}

	private void addItem(final String pText, final EnumPLAF pEnumPLAF) {

		JMenuItem mMenuItemAero = new JRadioButtonMenuItem(pText);
		add(mMenuItemAero);
		mMenuItemAero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				mCore.setLookAndFeel(pEnumPLAF);

				JOptionPane
						.showMessageDialog(
								JMenuLookAndFeel.this,
								"Изменения вступят в силу после перезагрузки приложенияю.",
								"Предупреждение",
								JOptionPane.WARNING_MESSAGE);
			}
		});

		EnumPLAF selectPLAF = Settings.getInstance().getPLookAndFeel();
		mMenuItemAero.setSelected(selectPLAF == pEnumPLAF);
		mGroup.add(mMenuItemAero);
		add(mMenuItemAero);
	}
}
