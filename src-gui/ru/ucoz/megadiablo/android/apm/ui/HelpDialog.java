package ru.ucoz.megadiablo.android.apm.ui;

import com.adbhelper.adb.AdbModule;
import ru.ucoz.megadiablo.android.apm.Consts;
import ru.ucoz.megadiablo.android.apm.help.HyperlinkListener;
import ru.ucoz.megadiablo.android.apm.ui.settings.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author MegaDiablo
 */
public class HelpDialog extends JDialog {

	private final String mBaseDirectory = "doc";
	private final String mLanguage;

	public HelpDialog(final JFrame pFrame) {
		this(pFrame, "ru");
	}

	public HelpDialog(final JFrame pFrame, String pLanguage) {
		super(pFrame);
		mLanguage = pLanguage;

		setTitle("Руководство пользователя");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setIconImage(Settings.getInstance().getDefaultApplicationIcon());
		setModal(true);

		setMinimumSize(new Dimension(640, 480));
		setSize(new Dimension(800, 600));
		setResizable(true);
		setAlwaysOnTop(true);

		HyperlinkListener hyperlinkListener = new HyperlinkListener();
		hyperlinkListener.addBaseDirectory(mBaseDirectory + File.separator + mLanguage);
		hyperlinkListener.addBaseDirectory(mBaseDirectory + File.separator + "ru"); // finding in default folder

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{252, 273, 0};
		gridBagLayout.rowHeights = new int[]{293, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);

		JEditorPane contents = new JEditorPane("text/html", hyperlinkListener.openMarkdown("index.md"));
		contents.setBackground(UIManager.getColor("Panel.background"));
		JScrollPane scrollPaneContents = new JScrollPane(contents);
		GridBagConstraints gbc_scrollPaneContents = new GridBagConstraints();
		gbc_scrollPaneContents.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneContents.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPaneContents.gridx = 0;
		gbc_scrollPaneContents.gridy = 0;
		getContentPane().add(scrollPaneContents, gbc_scrollPaneContents);
		contents.setEditable(false);
		contents.addHyperlinkListener(hyperlinkListener);

		JEditorPane content = new JEditorPane("text/html", hyperlinkListener.openMarkdown("introduce.md"));
		content.setBackground(UIManager.getColor("Panel.background"));
		JScrollPane scrollPane = new JScrollPane(content);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		getContentPane().add(scrollPane, gbc_scrollPane);
		content.setEditable(false);
		content.addHyperlinkListener(hyperlinkListener);
		hyperlinkListener.setSource(content);

		setLocationRelativeTo(null);
	}

	public String getLanguage() {
		return mLanguage;
	}
}
