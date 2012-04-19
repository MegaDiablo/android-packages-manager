package ru.ucoz.megadiablo.android.apm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.adbhelper.adb.AdbModule;

/**
 * @author MegaDiablo
 * */
public class AboutDialog extends JDialog {

	public AboutDialog(final JFrame pFrame) {
		super(pFrame);
		setTitle("О программе...");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setModal(true);

		setMinimumSize(new Dimension(530, 320));
		setResizable(false);
		setAlwaysOnTop(true);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel mIconPanel = new JPanel();
		getContentPane().add(mIconPanel, BorderLayout.CENTER);
		mIconPanel.setLayout(new BorderLayout(0, 0));

		JLabel label = new JLabel("");
		mIconPanel.add(label, BorderLayout.WEST);
		label.setIcon(new ImageIcon(AboutDialog.class
				.getResource("/res/apm_big_256.png")));

		JPanel mInfoPanel = new JPanel();
		mInfoPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		mIconPanel.add(mInfoPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mInfoPanel = new GridBagLayout();
		gbl_mInfoPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_mInfoPanel.rowHeights =
				new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 122, 0, 0 };
		gbl_mInfoPanel.columnWeights =
				new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_mInfoPanel.rowWeights =
				new double[] {
						0.0,
						0.0,
						1.0,
						0.0,
						0.0,
						0.0,
						0.0,
						0.0,
						1.0,
						0.0,
						Double.MIN_VALUE };
		mInfoPanel.setLayout(gbl_mInfoPanel);

		JLabel mLabelAppName = new JLabel("Android Package Manager");
		mLabelAppName.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_mLabelAppName = new GridBagConstraints();
		gbc_mLabelAppName.gridwidth = 2;
		gbc_mLabelAppName.insets = new Insets(0, 0, 5, 5);
		gbc_mLabelAppName.gridx = 0;
		gbc_mLabelAppName.gridy = 0;
		mInfoPanel.add(mLabelAppName, gbc_mLabelAppName);

		String ver =
				String.format(
						"Версия: %s / %s",
						Consts.VERSION_APP,
						AdbModule.VERSION_ADB_HELPER);

		JLabel mLabelVersion = new JLabel(ver);
		mLabelVersion.setFont(new Font("Tahoma", Font.ITALIC, 11));
		GridBagConstraints gbc_mLabelVersion = new GridBagConstraints();
		gbc_mLabelVersion.anchor = GridBagConstraints.EAST;
		gbc_mLabelVersion.gridwidth = 2;
		gbc_mLabelVersion.insets = new Insets(0, 0, 5, 5);
		gbc_mLabelVersion.gridx = 0;
		gbc_mLabelVersion.gridy = 1;
		mInfoPanel.add(mLabelVersion, gbc_mLabelVersion);

				JLabel mWordAutors = new JLabel("Авторы :");
				mWordAutors.setFont(new Font("Tahoma", Font.BOLD, 11));
				GridBagConstraints gbc_mWordAutors = new GridBagConstraints();
				gbc_mWordAutors.anchor = GridBagConstraints.WEST;
				gbc_mWordAutors.insets = new Insets(0, 0, 5, 5);
				gbc_mWordAutors.gridx = 0;
				gbc_mWordAutors.gridy = 3;
				mInfoPanel.add(mWordAutors, gbc_mWordAutors);

				JLabel mLabelAutorGUI = new JLabel("Громыко Александр");
				GridBagConstraints gbc_mLabelAutorGUI = new GridBagConstraints();
				gbc_mLabelAutorGUI.gridwidth = 2;
				gbc_mLabelAutorGUI.insets = new Insets(0, 0, 5, 0);
				gbc_mLabelAutorGUI.anchor = GridBagConstraints.WEST;
				gbc_mLabelAutorGUI.gridx = 1;
				gbc_mLabelAutorGUI.gridy = 3;
				mInfoPanel.add(mLabelAutorGUI, gbc_mLabelAutorGUI);

				JLabel mLabelAutorCore = new JLabel("Баразновский Владимир");
				GridBagConstraints gbc_mLabelAutorCore = new GridBagConstraints();
				gbc_mLabelAutorCore.gridwidth = 2;
				gbc_mLabelAutorCore.anchor = GridBagConstraints.WEST;
				gbc_mLabelAutorCore.insets = new Insets(0, 0, 5, 0);
				gbc_mLabelAutorCore.gridx = 1;
				gbc_mLabelAutorCore.gridy = 4;
				mInfoPanel.add(mLabelAutorCore, gbc_mLabelAutorCore);

				JLabel mYear = new JLabel("Год :");
				mYear.setFont(new Font("Tahoma", Font.BOLD, 11));
				GridBagConstraints gbc_mYear = new GridBagConstraints();
				gbc_mYear.anchor = GridBagConstraints.EAST;
				gbc_mYear.insets = new Insets(0, 0, 5, 5);
				gbc_mYear.gridx = 0;
				gbc_mYear.gridy = 6;
				mInfoPanel.add(mYear, gbc_mYear);

				JLabel mYearValue = new JLabel("2011-2012");
				GridBagConstraints gbc_mYearValue = new GridBagConstraints();
				gbc_mYearValue.gridwidth = 2;
				gbc_mYearValue.insets = new Insets(0, 0, 5, 0);
				gbc_mYearValue.anchor = GridBagConstraints.WEST;
				gbc_mYearValue.gridx = 1;
				gbc_mYearValue.gridy = 6;
				mInfoPanel.add(mYearValue, gbc_mYearValue);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 9;
		mInfoPanel.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));

		JButton mButtonOk = new JButton("Ok");
		panel.add(mButtonOk);
		mButtonOk.setMaximumSize(new Dimension(150, 23));
		mButtonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				AboutDialog.this.dispose();
			}
		});

		// TransparentBackground background = new TransparentBackground(this);
		// background.setBounds(0, 0, 320, 256);
		// getContentPane().add(background);

		setLocationRelativeTo(null);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -1875101022461842548L;
}
