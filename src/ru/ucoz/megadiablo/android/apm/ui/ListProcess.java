package ru.ucoz.megadiablo.android.apm.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ru.ucoz.megadiablo.android.apm.Events;
import ru.ucoz.megadiablo.android.apm.IEvent;

/**
 * @author MegaDiablo
 * */
public class ListProcess extends JDialog implements Events.IChangeStatus {

	{
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 7520529266205621545L;

	// private JList mListProcess = new JList();
	private JLabel mLabelName = new JLabel("Name");
	private JTextArea mTextAreaDescription = new JTextArea();
	private JLabel mLabelCount = new JLabel("New label");
	private JList mListProcess = new JList();

	public ListProcess() {
		setTitle("Список задач");
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(320, 240));
		setSize(new Dimension(320, 240));
		// setModal(true);
		// setModalityType(ModalityType.APPLICATION_MODAL);
		// setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		// setAlwaysOnTop(true);

		JPanel mMainPanel = new JPanel();
		mMainPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		getContentPane().add(mMainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mMainPanel = new GridBagLayout();
		gbl_mMainPanel.columnWidths = new int[] { 312, 0 };
		gbl_mMainPanel.rowHeights = new int[] { 0, 70, 0, 0, 0 };
		gbl_mMainPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_mMainPanel.rowWeights =
				new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		mMainPanel.setLayout(gbl_mMainPanel);

		GridBagConstraints gbc_mLabelName = new GridBagConstraints();
		gbc_mLabelName.insets = new Insets(0, 0, 5, 0);
		gbc_mLabelName.fill = GridBagConstraints.BOTH;
		gbc_mLabelName.gridx = 0;
		gbc_mLabelName.gridy = 0;
		mMainPanel.add(mLabelName, gbc_mLabelName);
		mTextAreaDescription.setFont(new Font("Monospaced", Font.PLAIN, 12));
		mTextAreaDescription.setLineWrap(true);

		mTextAreaDescription.setEditable(false);
		mTextAreaDescription.setWrapStyleWord(true);
		mTextAreaDescription.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_mTextAreaDescription = new GridBagConstraints();
		gbc_mTextAreaDescription.insets = new Insets(0, 0, 5, 0);
		gbc_mTextAreaDescription.fill = GridBagConstraints.BOTH;
		gbc_mTextAreaDescription.gridx = 0;
		gbc_mTextAreaDescription.gridy = 1;
		mMainPanel.add(mTextAreaDescription, gbc_mTextAreaDescription);

		GridBagConstraints gbc_mLabelCount = new GridBagConstraints();
		gbc_mLabelCount.insets = new Insets(0, 0, 5, 0);
		gbc_mLabelCount.anchor = GridBagConstraints.WEST;
		gbc_mLabelCount.gridx = 0;
		gbc_mLabelCount.gridy = 2;
		mMainPanel.add(mLabelCount, gbc_mLabelCount);

		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 3;
		mListProcess.setBorder(new LineBorder(new Color(0, 0, 0)));
		mMainPanel.add(mListProcess, gbc_list);

		setLocationRelativeTo(null);

		// mMainPanel.add(mListProcess, BorderLayout.CENTER);

		// mListProcess.setCellRenderer(new DefaultListCellRenderer() {
		//
		// /**
		// *
		// */
		// private static final long serialVersionUID = 6195853189087551828L;
		//
		// protected Border noFocusBorder = new EmptyBorder(15, 1, 1, 1);
		//
		// protected TitledBorder focusBorder = new TitledBorder(LineBorder
		// .createGrayLineBorder(), "title");
		//
		// protected DefaultListCellRenderer defaultRenderer = new
		// DefaultListCellRenderer();
		//
		// public Component getListCellRendererComponent(JList list,
		// Object value, int index, boolean isSelected,
		// boolean cellHasFocus) {
		//
		// JLabel renderer = (JLabel) defaultRenderer
		// .getListCellRendererComponent(list, value, index,
		// isSelected, cellHasFocus);
		//
		// if (value instanceof IEvent) {
		// IEvent event = (IEvent) value;
		// focusBorder.setTitle(event.getName());
		// renderer.setText("<html>" + event.getDescription()
		// + "</html>");
		// renderer.setBorder(focusBorder);
		// } else {
		// renderer.setBorder(noFocusBorder);
		// }
		//
		// return renderer;
		// }
		//
		// @Override
		// protected void paintComponent(Graphics g) {
		// super.paintComponent(g);
		// }
		//
		// @Override
		// public void paintAll(Graphics g) {
		// super.paintAll(g);
		// }
		// });
	}

	@Override
	public void changeStatus(final int pStatus) {
		// switch (pStatus) {
		// case START:
		// case WAKE:
		// setVisible(true);
		// break;
		//
		// case WAIT:
		// setVisible(false);
		// break;
		// }
	}

	@Override
	public void updateList(final List<IEvent> pEvents) {
		// DefaultListModel model = new DefaultListModel();
		//
		// if (pEvents != null) {
		// for (IEvent item : pEvents) {
		// model.addElement(item);
		// }
		// }
		//
		// mListProcess.setModel(model);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String text = "В очереди находится %s элементов";

				if (pEvents == null) {
					mLabelName.setText("Не известно");
					mTextAreaDescription.setText("");
					mLabelCount.setText(String.format(text, 0));
					return;
				}

				int size = Math.max(0, pEvents.size() - 1);
				mLabelCount.setText(String.format(text, size));

				if (pEvents.size() > 0) {
					IEvent event = pEvents.get(0);

					mLabelName.setText(event.getName());
					mTextAreaDescription.setText(event.getDescription());
				}

				DefaultListModel model = new DefaultListModel();
				for (int i = 1; i < pEvents.size(); i++) {
					IEvent item = pEvents.get(i);
					model.addElement(item.getName());
				}
				mListProcess.setModel(model);
			}
		});
	}

	@Override
	public void setVisible(final boolean b) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ListProcess.super.setVisible(b);
			}
		});
	}

}
