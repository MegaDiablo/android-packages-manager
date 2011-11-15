package ru.ucoz.megadiablo.android.apm.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import ru.ucoz.megadiablo.android.apm.Core;
import ru.ucoz.megadiablo.android.apm.Runner;

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
		addItem("Aero", EnumPLAF.AERO);
		addItem("HiFi", EnumPLAF.HIFI);
		addItem("Acry", EnumPLAF.ACRY);
		addItem("Aluminium", EnumPLAF.ALUMINIUM);
		addItem("Fast", EnumPLAF.FAST);
		addItem("McWin", EnumPLAF.MCWIN);
		addItem("Mint", EnumPLAF.MINT);
		addItem("Noire", EnumPLAF.NOIRE);
		addItem("Smart", EnumPLAF.SMART);
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
								"Предупреждение", JOptionPane.WARNING_MESSAGE);
			}
		});
		mMenuItemAero.setSelected(Runner.LOOK_AND_FEEL == pEnumPLAF);
		mGroup.add(mMenuItemAero);
		add(mMenuItemAero);
	}
}
