package ru.ucoz.megadiablo.android.apm.ui.log;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ru.ucoz.megadiablo.android.apm.Core;

import com.adbhelper.adb.ILogListener;
import ru.ucoz.megadiablo.android.apm.ui.settings.Settings;

/**
 *
 * @author Vladimir Baraznovsky
 *
 */
public class LogDialog extends JDialog implements ILogListener {
	private static final int COLUMN_TYPE = 1;
	private static final int COLUMN_TIME = 0;
	private static final String[] TITLE_LOG = new String[] {
			"\u0412\u0440\u0435\u043C\u044F",
			"\u0422\u0438\u043F",
			"\u0421\u043E\u043E\u0431\u0449\u0435\u043D\u0438\u0435" };

	private static final long serialVersionUID = -7228934472909425734L;
	private static final Object[] LIST_TYPES_FILTER = new Object[TypeMessage.values().length + 1];
	static {
		LIST_TYPES_FILTER[0] = "\u0412\u0435\u0441\u044c \u043b\u043e\u0433";
		int i = 1;
		for (TypeMessage typeMessage : TypeMessage.values()) {
			LIST_TYPES_FILTER[i] = typeMessage;
			i++;
		}
	}
	private Core mCore;
	private JTable tableLog;

	private DefaultTableModel mLogTableModel;

	private JButton btnClear;
	private DefaultRowSorter<? extends TableModel, ? extends Object> mSorter;
	private FilterByType mFilter;
	private JScrollPane mScrollPane;
	private JCheckBox mAutoscroll;

	/**
	 * Create the dialog.
	 */
	public LogDialog(final Core pCore) {
		mCore = pCore;
		setMinimumSize(new Dimension(320, 240));
		setSize(new Dimension(640, 480));
		setTitle("\u041b\u043e\u0433");
		setIconImage(Settings.getInstance().getDefaultApplicationIcon());

		mScrollPane = new JScrollPane();
		getContentPane().add(mScrollPane, BorderLayout.CENTER);

		tableLog = new JTable();
		tableLog.setFont(new Font("Dialog", Font.PLAIN, 10));

	//	mSorter.setRowFilter(new FilterByType());

		mLogTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			}
		};
		tableLog.setModel(mLogTableModel);

		initLog();
		mScrollPane.setViewportView(tableLog);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 98, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 25, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		btnClear = new JButton("\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c");
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

		JComboBox comboBox = new JComboBox();

		comboBox.setModel(new DefaultComboBoxModel(LIST_TYPES_FILTER));
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent e) {

				Object[] objects = e.getItemSelectable().getSelectedObjects();

				if (objects != null && objects.length > 0) {
					Object object = objects[0];
					if (object instanceof TypeMessage) {
						setFilterType((TypeMessage) object);
					} else {
						setFilterType(null);
					}

				}
			}


		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(5, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		panel_1.add(comboBox, gbc_comboBox);

		mAutoscroll = new JCheckBox("\u0421\u043b\u0435\u0434\u0438\u0442\u044c");
		mAutoscroll.setSelected(true);
		GridBagConstraints gbc_Autoscroll = new GridBagConstraints();
		gbc_Autoscroll.insets = new Insets(5, 0, 5, 5);
		gbc_Autoscroll.gridx = 2;
		gbc_Autoscroll.gridy = 0;
		panel_1.add(mAutoscroll, gbc_Autoscroll);



		mCore.setLogListener(this);
		mSorter = new TableRowSorter(mLogTableModel);

		mFilter = new FilterByType();
		mSorter.setRowFilter(mFilter);

		tableLog.setRowSorter(mSorter);

	}


	public void setFilterType(final TypeMessage type){
		mFilter.setFilteredType(type);
		mSorter.setRowFilter(mFilter);

	}
	private void initLog() {
		mLogTableModel.setDataVector(new Object[][] {}, TITLE_LOG);
		tableLog.getColumnModel().getColumn(COLUMN_TIME).setMaxWidth(300);
		tableLog.getColumnModel().getColumn(COLUMN_TIME).setPreferredWidth(80);
		tableLog.getColumnModel().getColumn(COLUMN_TYPE).setMaxWidth(200);
		tableLog.getColumnModel().getColumn(COLUMN_TYPE).setPreferredWidth(50);
		int countColumn = tableLog.getColumnCount();
		int[] widhts = new int[countColumn];
		TypedColumnCellRenderer typedColumnCellRenderer = new TypedColumnCellRenderer();
		for (int i = 0; i < widhts.length; i++) {
			tableLog.getColumnModel().getColumn(i).setCellRenderer(typedColumnCellRenderer);
		}
		tableLog.updateUI();

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
		if (mAutoscroll.isSelected()){
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				int max = mScrollPane.getVerticalScrollBar().getMaximum();
				mScrollPane.getVerticalScrollBar().setValue(max);
			}
		});
		}
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

		addRowMessage(TypeMessage.DEBUG, pTime, pMessage);

	}

	static class FilterByType extends RowFilter<TableModel, Object> {
		private TypeMessage mFilteredType = null;
		@Override
		public boolean include(final javax.swing.RowFilter.Entry<? extends TableModel, ? extends Object> entry) {
			if (mFilteredType==null){
				return true;
			}
			int count = entry.getValueCount();
			for (int i = 0; i < count; i++) {
				Message value = (Message) entry.getValue(i);
				if (value.getTypeMessage() == mFilteredType){
					return true;
				}
			}

			return false;
		}
		public TypeMessage getFilteredType() {
			return mFilteredType;
		}
		public void setFilteredType(final TypeMessage filteredType) {
			mFilteredType = filteredType;
		}
	}

	public static class TypedColumnCellRenderer extends DefaultTableCellRenderer {


		@Override
		  public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int col) {

			Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
			if (value instanceof Message) {
				Message message = (Message) value;
				component.setForeground(message.getTypeMessage().getColor());
			}
			JLabel label = (JLabel) component;
			if (col == COLUMN_TYPE) {

				label.setHorizontalAlignment(JLabel.CENTER);
			} else {
				label.setHorizontalAlignment(JLabel.LEFT);
			}
			return component;

		}
}
}
