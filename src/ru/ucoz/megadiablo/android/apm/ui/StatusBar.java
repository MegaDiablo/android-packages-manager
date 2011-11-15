package ru.ucoz.megadiablo.android.apm.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ru.ucoz.megadiablo.android.apm.Core;
import ru.ucoz.megadiablo.android.apm.Events;
import ru.ucoz.megadiablo.android.apm.IEvent;


/**
 * @author MegaDiablo
 * */
public class StatusBar extends JToolBar implements Events.IChangeStatus {

	/**
	 *
	 */
	private static final long serialVersionUID = -3710025172478557577L;

	private Core mCore = null;

	private JLabel mLabelTaskCount;
	private JSeparator separator;
	private JProgressBar mLabelLastTaskDescription;
	private JButton mTerminate;

	public StatusBar(final Core pCore) {
		setMinimumSize(new Dimension(35, 50));
		setMaximumSize(new Dimension(35, 50));

		mCore = pCore;

		JPanel panel = new JPanel();
		add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		mLabelTaskCount = new JLabel("");
		GridBagConstraints gbc_mLabelTaskCount = new GridBagConstraints();
		gbc_mLabelTaskCount.insets = new Insets(0, 0, 0, 5);
		gbc_mLabelTaskCount.gridx = 0;
		gbc_mLabelTaskCount.gridy = 0;
		panel.add(mLabelTaskCount, gbc_mLabelTaskCount);

		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.insets = new Insets(0, 0, 0, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 0;
		panel.add(separator, gbc_separator);

		mLabelLastTaskDescription = new JProgressBar();
		mLabelLastTaskDescription.setStringPainted(true);
		mLabelLastTaskDescription.setIndeterminate(true);
		mLabelLastTaskDescription.setString("");
		mLabelLastTaskDescription.setMaximum(0);
		mLabelLastTaskDescription.setToolTipText("");
		GridBagConstraints gbc_mLabelLastTaskDescription = new GridBagConstraints();
		gbc_mLabelLastTaskDescription.insets = new Insets(0, 0, 0, 5);
		gbc_mLabelLastTaskDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_mLabelLastTaskDescription.gridx = 2;
		gbc_mLabelLastTaskDescription.gridy = 0;
		panel.add(mLabelLastTaskDescription, gbc_mLabelLastTaskDescription);

		mTerminate = new JButton("");
		mTerminate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mCore.terminatedCurrentTask();
			}
		});
		mTerminate.setEnabled(false);
		mTerminate.setFocusable(false);
		mTerminate.setFocusPainted(false);
		mTerminate.setOpaque(false);
		mTerminate.setBorder(new EmptyBorder(0, 0, 0, 0));
		mTerminate.setIcon(new ImageIcon(StatusBar.class
				.getResource("/res/terminate.png")));
		mTerminate.setHorizontalTextPosition(SwingConstants.CENTER);
		GridBagConstraints gbc_mTerminate = new GridBagConstraints();
		gbc_mTerminate.gridx = 3;
		gbc_mTerminate.gridy = 0;
		panel.add(mTerminate, gbc_mTerminate);
	}

	@Override
	public void changeStatus(int pStatus) {
		if (pStatus == Events.IChangeStatus.START
				|| pStatus == Events.IChangeStatus.WAKE) {

			mTerminate.setEnabled(true);
		} else {
			mTerminate.setEnabled(false);
		}
	}

	@Override
	public void updateList(List<IEvent> pEvents) {
		String text = "Осталось %s";
		String textQueue = "Сейчас : %s";

		if (pEvents == null) {
			mLabelLastTaskDescription.setString(String.format(textQueue,
					"- none -"));
			mLabelLastTaskDescription.setIndeterminate(false);
			return;
		}

		int size = Math.max(0, pEvents.size() - 1);
		mLabelTaskCount.setText(String.format(text, size));
		//
		if (pEvents.size() > 0) {
			IEvent event = pEvents.get(0);
			mLabelLastTaskDescription.setString(String.format(textQueue,
					event.getDescription()));
			mLabelLastTaskDescription.setIndeterminate(true);
		} else {
			mLabelLastTaskDescription.setString(String.format(textQueue,
					"- none -"));
			mLabelLastTaskDescription.setIndeterminate(false);
		}
		//
		// DefaultListModel model = new DefaultListModel();
		// for (int i = 1; i < pEvents.size(); i++) {
		// IEvent item = pEvents.get(i);
		// model.addElement(item.getName());
		// }
		// mListProcess.setModel(model);
	}

}
