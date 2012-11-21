package ru.ucoz.megadiablo.android.apm.ui.log;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ru.ucoz.megadiablo.android.apm.Core;
/**
 *
 * @author Vladimir Baraznovsky
 *
 */
public class LogDialog extends JDialog {

	private static final long serialVersionUID = -7228934472909425734L;

	private Core mCore;
	private JTable tableLog;

	/**
	 * Create the dialog.
	 */
	public LogDialog(final Core pCore) {
		setModal(true);
		mCore = pCore;
		setMinimumSize(new Dimension(320, 240));
		setTitle("Лог");

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{98, 0};
		gbl_panel_1.rowHeights = new int[]{25, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);

				JButton btnClear = new JButton("Очистить");
				GridBagConstraints gbc_btnClear = new GridBagConstraints();
				gbc_btnClear.insets = new Insets(5, 5, 5, 5);
				gbc_btnClear.anchor = GridBagConstraints.LINE_START;
				gbc_btnClear.gridx = 0;
				gbc_btnClear.gridy = 0;
				panel_1.add(btnClear, gbc_btnClear);

		tableLog = new JTable();
		tableLog.setRowSelectionAllowed(false);
		tableLog.setColumnSelectionAllowed(true);
		tableLog.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"\u0412\u0440\u0435\u043C\u044F", "\u0422\u0438\u043F", "\u0421\u043E\u043E\u0431\u0449\u0435\u043D\u0438\u0435"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			@Override
			public boolean isCellEditable(final int row, final int column) {
				return columnEditables[column];
			}
		});
		tableLog.getColumnModel().getColumn(0).setResizable(false);
		tableLog.getColumnModel().getColumn(0).setPreferredWidth(116);
		tableLog.getColumnModel().getColumn(0).setMaxWidth(250);
		tableLog.getColumnModel().getColumn(1).setResizable(false);
		tableLog.getColumnModel().getColumn(1).setMaxWidth(75);
		tableLog.getColumnModel().getColumn(2).setPreferredWidth(226);
		getContentPane().add(tableLog, BorderLayout.CENTER);

	}
}
