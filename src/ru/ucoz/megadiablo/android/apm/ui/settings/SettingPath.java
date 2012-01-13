package ru.ucoz.megadiablo.android.apm.ui.settings;

import java.awt.Dialog.ModalityType;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ru.ucoz.megadiablo.android.apm.ui.JFolderChoose;

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

					String folder = mFolderChoose.getSelectedFolder();

					StringBuilder template = new StringBuilder();

					template.append("%s");

					if (!folder.startsWith(File.separator)) {
						template.append(File.separator);
					}

					template.append("%s");

					if (!folder.endsWith(File.separator)) {
						template.append(File.separator);
					}

					template.append("%s");

					mTextFieldValue.setText(String.format(
							template.toString(),
							mPrefixPath,
							folder,
							mPostfixPath));
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

	public String getPrefixPath() {
		return mPrefixPath;
	}

	public void setPrefixPath(final String pPrefixPath) {
		if (pPrefixPath != null && !pPrefixPath.trim().isEmpty()) {
			this.mPrefixPath = pPrefixPath.trim();
		} else {
			this.mPrefixPath = "";
		}
	}

	public String getPostfixPath() {
		return mPostfixPath;
	}

	public void setPostfixPath(final String pPostfixPath) {
		if (pPostfixPath != null && !pPostfixPath.trim().isEmpty()) {
			this.mPostfixPath = pPostfixPath.trim();
		} else {
			this.mPostfixPath = "";
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5800986722665541381L;
	private JTextField mTextFieldValue;
	private JLabel mLabelName;
	private JButton mButtonBrowse;
	private JFolderChoose mFolderChoose;

	private String mPrefixPath = "";
	private String mPostfixPath = "";

}
