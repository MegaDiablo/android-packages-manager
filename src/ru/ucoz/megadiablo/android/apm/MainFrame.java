package ru.ucoz.megadiablo.android.apm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import ru.ucoz.megadiablo.android.apm.ui.ControllPanel;
import ru.ucoz.megadiablo.android.apm.ui.ListPackages;
import ru.ucoz.megadiablo.android.apm.ui.MainMenuBar;
import ru.ucoz.megadiablo.android.apm.ui.SearchPackages;
import ru.ucoz.megadiablo.android.apm.ui.StatusBar;
import ru.ucoz.megadiablo.android.apm.ui.UserInterfaceUtils;

/**
 * @author MegaDiablo
 * */
public class MainFrame extends JFrame {

	static {
		UserInterfaceUtils.setLookAndFeel(Settings.getInstance()
				.getPLookAndFeel());
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -6543684206349648011L;
	private JPanel mMainPanel;
	private ListPackages mListPackages;
	private SearchPackages mSearchPackages;

	private Core mCore;
	private MainMenuBar mMainMenuBar;
	private StatusBar statusBar;
	private JSeparator separator;

	public MainFrame(final Core pCore, final Events pEvents,
			final List<EventUpdater> pListEventUpdaters) {

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MainFrame.class.getResource("/res/apm64.png")));
		mCore = pCore;

		setSize(new Dimension(640, 480));
		setMinimumSize(new Dimension(640, 480));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Android Package Manager");

		mMainMenuBar = new MainMenuBar(pCore, pListEventUpdaters, this);
		setJMenuBar(mMainMenuBar);

		mMainPanel = new JPanel();
		getContentPane().add(mMainPanel, BorderLayout.CENTER);
		GridBagLayout gbl_mMainPanel = new GridBagLayout();
		gbl_mMainPanel.columnWidths = new int[] { 0, 0 };
		gbl_mMainPanel.rowHeights = new int[] { 357, 7, 0, 0 };
		gbl_mMainPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_mMainPanel.rowWeights = new double[] { 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		mMainPanel.setLayout(gbl_mMainPanel);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		mMainPanel.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel mLabelListPackages = new JLabel(
				"\u0421\u043F\u0438\u0441\u043E\u043A \u043F\u0430\u043A\u0435\u0442\u043E\u0432");
		mLabelListPackages.setFont(new Font("Tahoma", Font.BOLD, 11));
		mLabelListPackages.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Bug
				mCore.refreshPackages();
			}
		});
		GridBagConstraints gbc_mLabelListPackages = new GridBagConstraints();
		gbc_mLabelListPackages.insets = new Insets(0, 0, 5, 0);
		gbc_mLabelListPackages.gridx = 0;
		gbc_mLabelListPackages.gridy = 0;
		panel.add(mLabelListPackages, gbc_mLabelListPackages);

		mListPackages = new ListPackages(pCore);
		GridBagConstraints gbc_listPackages = new GridBagConstraints();
		gbc_listPackages.insets = new Insets(0, 0, 5, 0);
		gbc_listPackages.fill = GridBagConstraints.BOTH;
		gbc_listPackages.gridx = 0;
		gbc_listPackages.gridy = 1;
		panel.add(mListPackages, gbc_listPackages);

		mSearchPackages = new SearchPackages(pCore, mListPackages);
		GridBagConstraints gbc_mSearchPackages = new GridBagConstraints();
		gbc_mSearchPackages.fill = GridBagConstraints.BOTH;
		gbc_mSearchPackages.gridx = 0;
		gbc_mSearchPackages.gridy = 2;
		panel.add(mSearchPackages, gbc_mSearchPackages);

		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		mMainPanel.add(separator, gbc_separator);

		ControllPanel controllPanel = new ControllPanel(pCore);
		controllPanel.setBorder(new EmptyBorder(0, 0, 3, 0));
		GridBagConstraints gbc_controllPanel = new GridBagConstraints();
		gbc_controllPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_controllPanel.gridx = 0;
		gbc_controllPanel.gridy = 2;
		mMainPanel.add(controllPanel, gbc_controllPanel);

		statusBar = new StatusBar(pCore);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
		if (pEvents != null) {
			pEvents.addChangeStatusListener(statusBar);
		}

		setLocationRelativeTo(null);
	}

	public void setFilter(String pFilter) {
		mSearchPackages.setFilter(pFilter);
	}

	public String getFilter() {
		return mSearchPackages.getFilter();
	}

	public void close() {
		this.processWindowEvent(new WindowEvent(this,
				WindowEvent.WINDOW_CLOSING));
	}

}
