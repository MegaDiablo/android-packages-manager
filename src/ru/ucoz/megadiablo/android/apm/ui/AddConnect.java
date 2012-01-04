package ru.ucoz.megadiablo.android.apm.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author MegaDiablo
 * */
public class AddConnect extends JDialog {
	/**
	 *
	 */
	private static final long serialVersionUID = 7778519660652728594L;
	private JTextField mTextField;

	private int mResult = JOptionPane.CLOSED_OPTION;

	public AddConnect() {
		setMinimumSize(new Dimension(300, 83));
		getContentPane().setMinimumSize(new Dimension(300, 170));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setModal(true);
		setTitle("Создание соединения");
		setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(3, 3, 3, 3));
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel.columnWeights =
				new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel mLabelNewName = new JLabel("Адрес :");
		GridBagConstraints gbc_mLabelNewName = new GridBagConstraints();
		gbc_mLabelNewName.insets = new Insets(0, 0, 5, 5);
		gbc_mLabelNewName.anchor = GridBagConstraints.EAST;
		gbc_mLabelNewName.gridx = 0;
		gbc_mLabelNewName.gridy = 0;
		panel.add(mLabelNewName, gbc_mLabelNewName);

		mTextField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridwidth = 3;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel.add(mTextField, gbc_textField);
		mTextField.setColumns(10);

		JButton btnOk = new JButton("Создать");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mResult = JOptionPane.OK_OPTION;
				AddConnect.this.dispose();
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 2;
		gbc_btnOk.gridy = 1;
		panel.add(btnOk, gbc_btnOk);

		JButton mButtonCancel = new JButton("Отмена");
		mButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mResult = JOptionPane.CANCEL_OPTION;
				AddConnect.this.dispose();
			}
		});
		GridBagConstraints gbc_mButtonCancel = new GridBagConstraints();
		gbc_mButtonCancel.gridx = 3;
		gbc_mButtonCancel.gridy = 1;
		panel.add(mButtonCancel, gbc_mButtonCancel);

		setLocationRelativeTo(null);
	}

	public String getAdress() {
		return mTextField.getText();
	}

	public int getResult() {
		return mResult;
	}

}
