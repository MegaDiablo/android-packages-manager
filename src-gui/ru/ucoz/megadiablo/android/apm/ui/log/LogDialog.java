package ru.ucoz.megadiablo.android.apm.ui.log;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ru.ucoz.megadiablo.android.apm.Core;

import com.adbhelper.adb.ILogListener;

/**
 *
 * @author Vladimir Baraznovsky
 *
 */
public class LogDialog extends JDialog implements ILogListener {

	private static final String[] TITLE_LOG = new String[] {
			"\u0412\u0440\u0435\u043C\u044F",
			"\u0422\u0438\u043F",
			"\u0421\u043E\u043E\u0431\u0449\u0435\u043D\u0438\u0435" };

	private static final long serialVersionUID = -7228934472909425734L;

	private Core mCore;
	private JTable tableLog;

	private DefaultTableModel mLogTableModel;

	private JButton btnClear;

	/**
	 * Create the dialog.
	 */
	public LogDialog(final Core pCore) {
		mCore = pCore;
		setMinimumSize(new Dimension(320, 240));
		setSize(new Dimension(640, 480));
		setTitle("Лог");

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		tableLog = new JTable();
		mLogTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		};
		tableLog.setModel(mLogTableModel);
		initLog();
		scrollPane.setViewportView(tableLog);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 98, 0 };
		gbl_panel_1.rowHeights = new int[] { 25, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		btnClear = new JButton("Очистить");
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				clearLog();
			}

		});
		GridBagConstraints gbc_btnClear = new GridBagConstraints();
		gbc_btnClear.insets = new Insets(5, 5, 5, 5);
		gbc_btnClear.anchor = GridBagConstraints.LINE_START;
		gbc_btnClear.gridx = 0;
		gbc_btnClear.gridy = 0;
		panel_1.add(btnClear, gbc_btnClear);

		mCore.setLogListener(this);
	}

	private void initLog() {
		mLogTableModel.setDataVector(new Object[][] {}, TITLE_LOG);
		tableLog.getColumnModel().getColumn(0).setPreferredWidth(140);
		tableLog.getColumnModel().getColumn(0).setMaxWidth(300);
		tableLog.getColumnModel().getColumn(1).setMaxWidth(50);

		tableLog.getColumnModel().getColumn(2).setPreferredWidth(226);
		int countColumn = tableLog.getColumnCount();
		int[] widhts = new int[countColumn];
		TypedColumnCellRenderer typedColumnCellRenderer = new TypedColumnCellRenderer();
		for (int i = 0; i < widhts.length; i++) {
			tableLog.getColumnModel().getColumn(i).setCellRenderer(typedColumnCellRenderer);
		}

	}

	protected void clearLog() {
		int countColumn = tableLog.getColumnCount();
		int[] widhts = new int[countColumn];
		for (int i = 0; i < widhts.length; i++) {
			widhts[i] = tableLog.getColumnModel().getColumn(i).getWidth();
		}
		initLog();
		for (int i = 0; i < widhts.length; i++) {
			tableLog.getColumnModel().getColumn(i).setPreferredWidth(widhts[i]);
		}

	}

	public void addRowLog(final Object pTime,
			final Object pType,
			final Object pMessage) {
		mLogTableModel.addRow(new Object[] { pTime, pType, pMessage });
	}



	@Override
	public void onInfo(final long pTime, final String pMessage) {
		addRowMessage(TypeMessage.INFO, pTime, pMessage);
	}


	protected void addRowMessage(final TypeMessage pType,
			final long pTime,
			final String pMessage) {
		Message time = Message.createMessageTime(pType, pTime);
		Message type = Message.createMessageType(pType);
		Message message = Message.createMessage(pType, pMessage);
		addRowLog(time, type, message);
	}

	@Override
	public void onError(final long pTime, final String pMessage) {
		addRowMessage(TypeMessage.ERROR, pTime, pMessage);

	}

	@Override
	public void onDebug(final long pTime, final String pMessage) {

		addRowMessage(TypeMessage.ERROR, pTime, pMessage);

	}

	public class TypedColumnCellRenderer extends DefaultTableCellRenderer {
		  @Override
		  public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int col) {

			Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

			if (value instanceof Message) {
				Message message = (Message) value;
				component.setForeground(message.getTypeMessage().getColor());
			}

		  //Return the JLabel which renders the cell.
		  return component;

		}
};
}
