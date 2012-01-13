package ru.ucoz.megadiablo.android.apm.ui.settings;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.GridBagLayout;
import java.awt.Window;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;

import ru.ucoz.megadiablo.android.apm.ui.JFolderChoose;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EmptyBorder;

/**
 * The Class SettingPath.
 */
public class SettingPath extends JPanel {

	public SettingPath(final Window pOwner) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights =
				new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		mLabelName = new JLabel("New label");
		GridBagConstraints gbcLabelName = new GridBagConstraints();
		gbcLabelName.insets = new Insets(0, 0, 0, 5);
		gbcLabelName.anchor = GridBagConstraints.EAST;
		gbcLabelName.gridx = 0;
		gbcLabelName.gridy = 0;
		add(mLabelName, gbcLabelName);

		mTextFieldValue = new JTextField();
		GridBagConstraints gbcTextFieldValue = new GridBagConstraints();
		gbcTextFieldValue.insets = new Insets(0, 0, 0, 5);
		gbcTextFieldValue.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldValue.gridx = 1;
		gbcTextFieldValue.gridy = 0;
		add(mTextFieldValue, gbcTextFieldValue);
		mTextFieldValue.setColumns(10);

		mButtonBrowse = new JButton("...");
		mButtonBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (mFolderChoose == null) {
					mFolderChoose = new JFolderChoose(pOwner);
					mFolderChoose.setModalityType(ModalityType.DOCUMENT_MODAL);
					mFolderChoose.setModal(true);
				}

				int result = mFolderChoose.open();

				if (result == JFileChooser.APPROVE_OPTION) {
					mTextFieldValue.setText(mFolderChoose.getSelectedFolder());
				}
			}
		});
		GridBagConstraints gbcButtonBrowse = new GridBagConstraints();
		gbcButtonBrowse.gridx = 2;
		gbcButtonBrowse.gridy = 0;
		add(mButtonBrowse, gbcButtonBrowse);
	}

	public void setLabel(final String pValue) {
		mLabelName.setText(pValue);
	}

	public String getLabel() {
		return mLabelName.getText();
	}

	public void setPath(final String pValue) {
		mTextFieldValue.setText(pValue);
	}

	public String getPath() {
		return mTextFieldValue.getText();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5800986722665541381L;
	private JTextField mTextFieldValue;
	private JLabel mLabelName;
	private JButton mButtonBrowse;
	private JFolderChoose mFolderChoose;

}
