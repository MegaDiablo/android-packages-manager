package ru.ucoz.megadiablo.android.apm.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * Swing, Third Edition
 * 
 * @author Matthew Robinson
 * @author Pavel Vorobiev
 * @author MegaDiablo
 * */
public class JFolderChoose extends JDialog {
	/**
	 *
	 */
	private static final long serialVersionUID = -4021685121338488922L;
	public static final Icon ICON_COMPUTER = null;
	public static final Icon ICON_TREE_OPEN = UIManager
			.getIcon("Tree.openIcon");
	public static final Icon ICON_TREE_CLOSED = UIManager
			.getIcon("Tree.closedIcon");

	protected JTree mTree;
	protected DefaultTreeModel mModel;
	protected JTextField mDisplay;
	private JPanel mPanelControll;
	private JPanel mPanelButtons;
	private JButton mNewFolder;
	private JButton mOk;
	private JButton mCancel;
	private JPanel panel;

	private int mLastStatus = 0;

	/**
	 * @wbp.parser.constructor
	 */
	public JFolderChoose(Window owner) {
		this(owner, "Обзор компьютера");
	}

	public JFolderChoose(Window owner, String title) {
		super(owner, title);
		setSize(400, 300);

		DefaultMutableTreeNode top = new DefaultMutableTreeNode(new IconData(
				ICON_COMPUTER, null, "Компьютер"));

		DefaultMutableTreeNode node;
		File[] roots = File.listRoots();
		for (int k = 0; k < roots.length; k++) {

			FileNode fileNode = new FileNode(roots[k], true);

			node = new DefaultMutableTreeNode(new IconData(fileNode.getIcon(),
					null, fileNode));
			top.add(node);
			node.add(new DefaultMutableTreeNode(new Boolean(true)));
		}

		mModel = new DefaultTreeModel(top);

		TreeCellRenderer renderer = new IconCellRenderer();

		panel = new JPanel();
		panel.setBorder(new EmptyBorder(3, 3, 3, 3));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		{
			mTree = new JTree(mModel);
			mTree.setRootVisible(false);

			mTree.putClientProperty("JTree.lineStyle", "Angled");
			mTree.setCellRenderer(renderer);

			mTree.addTreeExpansionListener(new DirExpansionListener());
			mTree.addTreeSelectionListener(new DirSelectionListener());

			mTree.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
			mTree.setShowsRootHandles(true);
			mTree.setEditable(false);

			JScrollPane s = new JScrollPane();
			panel.add(s, BorderLayout.CENTER);
			s.setViewportView(mTree);

		}

		mPanelControll = new JPanel();
		mPanelControll.setBorder(new EmptyBorder(3, 0, 0, 0));
		panel.add(mPanelControll, BorderLayout.SOUTH);
		mPanelControll.setLayout(new BorderLayout(0, 0));

		mDisplay = new JTextField();
		mPanelControll.add(mDisplay, BorderLayout.NORTH);
		mDisplay.setEditable(false);

		mPanelButtons = new JPanel();
		mPanelButtons.setBorder(new EmptyBorder(3, 0, 0, 0));
		mPanelControll.add(mPanelButtons, BorderLayout.SOUTH);
		GridBagLayout gbl_mPanelButtons = new GridBagLayout();
		gbl_mPanelButtons.columnWidths = new int[] { 150, 78, 0, 0 };
		gbl_mPanelButtons.rowHeights = new int[] { 23, 0 };
		gbl_mPanelButtons.columnWeights = new double[] { 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_mPanelButtons.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		mPanelButtons.setLayout(gbl_mPanelButtons);

		mNewFolder = new JButton("Новая папка");
		mNewFolder.setEnabled(false);
		GridBagConstraints gbc_mNewFolder = new GridBagConstraints();
		gbc_mNewFolder.insets = new Insets(0, 0, 0, 5);
		gbc_mNewFolder.anchor = GridBagConstraints.NORTHWEST;
		gbc_mNewFolder.gridx = 0;
		gbc_mNewFolder.gridy = 0;
		mPanelButtons.add(mNewFolder, gbc_mNewFolder);

		mOk = new JButton("Ок");
		mOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close(JFileChooser.APPROVE_OPTION);
			}
		});
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_button_1.insets = new Insets(0, 0, 0, 5);
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 0;
		mPanelButtons.add(mOk, gbc_button_1);

		mCancel = new JButton("Отмена");
		mCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close(JFileChooser.CANCEL_OPTION);
			}
		});
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.gridx = 2;
		gbc_button_2.gridy = 0;
		mPanelButtons.add(mCancel, gbc_button_2);

		setVisible(false);

		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setLocationRelativeTo(null);
	}

	private void close(int pStatus) {
		mLastStatus = pStatus;
		setVisible(false);
	}

	public int open() {
		mLastStatus = JFileChooser.CANCEL_OPTION;
		setVisible(true);
		return mLastStatus;
	};

	DefaultMutableTreeNode getTreeNode(TreePath path) {
		return (DefaultMutableTreeNode) (path.getLastPathComponent());
	}

	FileNode getFileNode(DefaultMutableTreeNode node) {
		if (node == null)
			return null;
		Object obj = node.getUserObject();
		if (obj instanceof IconData)
			obj = ((IconData) obj).getObject();
		if (obj instanceof FileNode)
			return (FileNode) obj;
		else
			return null;
	}

	// Make sure expansion is threaded and updating the tree model
	// only occurs within the event dispatching thread.
	class DirExpansionListener implements TreeExpansionListener {
		public void treeExpanded(TreeExpansionEvent event) {
			final DefaultMutableTreeNode node = getTreeNode(event.getPath());
			final FileNode fnode = getFileNode(node);

			Thread runner = new Thread() {
				public void run() {
					if (fnode != null && fnode.expand(node)) {
						Runnable runnable = new Runnable() {
							public void run() {
								mModel.reload(node);
							}
						};
						SwingUtilities.invokeLater(runnable);
					}
				}
			};
			runner.start();
		}

		public void treeCollapsed(TreeExpansionEvent event) {
		}
	}

	class DirSelectionListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent event) {
			DefaultMutableTreeNode node = getTreeNode(event.getPath());
			FileNode fnode = getFileNode(node);
			if (fnode != null)
				mDisplay.setText(fnode.getFile().getAbsolutePath());
			else
				mDisplay.setText("");
		}
	}

	public String getSelectedFolder() {
		return mDisplay.getText();
	}

}

class IconCellRenderer extends JLabel implements TreeCellRenderer {
	/**
	 *
	 */
	private static final long serialVersionUID = 1824019337797815674L;
	protected Color mTextSelectionColor;
	protected Color mTextNonSelectionColor;
	protected Color mBkSelectionColor;
	protected Color mBkNonSelectionColor;
	protected Color mBorderSelectionColor;

	protected boolean mSelected;

	public IconCellRenderer() {
		super();
		mTextSelectionColor = UIManager.getColor("Tree.selectionForeground");
		mTextNonSelectionColor = UIManager.getColor("Tree.textForeground");
		mBkSelectionColor = UIManager.getColor("Tree.selectionBackground");
		mBkNonSelectionColor = UIManager.getColor("Tree.textBackground");
		mBorderSelectionColor = UIManager.getColor("Tree.selectionBorderColor");
		setOpaque(false);
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus)

	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object obj = node.getUserObject();
		setText(obj.toString());

		if (obj instanceof Boolean)
			setText("Retrieving data...");

		if (obj instanceof IconData) {
			IconData idata = (IconData) obj;
			if (expanded)
				setIcon(idata.getExpandedIcon());
			else
				setIcon(idata.getIcon());
		} else
			setIcon(null);

		setFont(tree.getFont());
		setForeground(sel ? mTextSelectionColor : mTextNonSelectionColor);
		setBackground(sel ? mBkSelectionColor : mBkNonSelectionColor);
		mSelected = sel;
		return this;
	}

	public void paintComponent(Graphics g) {
		Color bColor = getBackground();
		Icon icon = getIcon();

		g.setColor(bColor);
		int offset = 0;
		if (icon != null && getText() != null)
			offset = (icon.getIconWidth() + getIconTextGap());
		g.fillRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);

		if (mSelected) {
			g.setColor(mBorderSelectionColor);
			g.drawRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
		}
		super.paintComponent(g);
	}
}

class IconData {
	protected Icon mIcon;
	protected Icon mExpandedIcon;
	protected Object mData;

	public IconData(Icon icon, Object data) {
		mIcon = icon;
		mExpandedIcon = null;
		mData = data;
	}

	public IconData(Icon icon, Icon expandedIcon, Object data) {
		mIcon = icon;
		mExpandedIcon = expandedIcon;
		mData = data;
	}

	public Icon getIcon() {
		return mIcon;
	}

	public Icon getExpandedIcon() {
		return mExpandedIcon != null ? mExpandedIcon : mIcon;
	}

	public Object getObject() {
		return mData;
	}

	public String toString() {
		return mData.toString();
	}
}

class FileNode {
	protected File mFile;
	protected Icon mIconOpen = null;
	protected Icon mIconClose = null;
	protected boolean mRoot = false;

	public FileNode(File file, boolean root) {
		mFile = file;
		mRoot = root;
	}

	public FileNode(File file) {
		this(file, false);
	}

	public File getFile() {
		return mFile;
	}

	public String toString() {
		return mFile.getName().length() > 0 ? mFile.getName() : mFile.getPath();
	}

	public Icon getIcon() {
		return getIcon(false);
	}

	public Icon getIcon(boolean open) {
		if (mIconOpen == null) {
			if (mRoot) {
				mIconOpen = FileSystemView.getFileSystemView().getSystemIcon(
						mFile);
				mIconClose = mIconOpen;
			}

			if (mIconOpen == null) {
				if (mFile.isDirectory()) {
					mIconOpen = JFolderChoose.ICON_TREE_OPEN;
					mIconClose = JFolderChoose.ICON_TREE_CLOSED;
				} else {
					mIconOpen = FileSystemView.getFileSystemView()
							.getSystemIcon(mFile);
					mIconClose = FileSystemView.getFileSystemView()
							.getSystemIcon(mFile);
				}
			}
		}

		return (open ? mIconOpen : mIconClose);
	}

	public boolean expand(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode flag = (DefaultMutableTreeNode) parent
				.getFirstChild();
		if (flag == null) { // No flag
			return false;
		}
		Object obj = flag.getUserObject();
		if (!(obj instanceof Boolean)) {
			return false; // Already expanded
		}

		parent.removeAllChildren(); // Remove Flag

		File[] files = listFiles();
		if (files == null) {
			return true;
		}

		List<FileNode> v = new ArrayList<FileNode>();

		for (int k = 0; k < files.length; k++) {
			File f = files[k];
			if (!(f.isDirectory())) {
				continue;
			}

			FileNode newNode = new FileNode(f);

			boolean isAdded = false;
			for (int i = 0; i < v.size(); i++) {
				FileNode nd = v.get(i);
				if (newNode.compareTo(nd) < 0) {
					v.add(i, newNode); // insertElementAt(newNode, i);
					isAdded = true;
					break;
				}
			}
			if (!isAdded) {
				// v.addElement(newNode);
				v.add(newNode);
			}
		}

		for (int i = 0; i < v.size(); i++) {
			FileNode nd = v.get(i);
			IconData idata = new IconData(nd.getIcon(false), nd.getIcon(true),
					nd);
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(idata);
			parent.add(node);

			if (nd.hasSubDirs()) {
				node.add(new DefaultMutableTreeNode(new Boolean(true)));
			}
		}

		return true;
	}

	public boolean hasSubDirs() {
		File[] files = listFiles();
		if (files == null)
			return false;
		for (int k = 0; k < files.length; k++) {
			if (files[k].isDirectory())
				return true;
		}
		return false;
	}

	public int compareTo(FileNode toCompare) {
		return mFile.getName().compareToIgnoreCase(toCompare.mFile.getName());
	}

	protected File[] listFiles() {
		if (!mFile.isDirectory())
			return null;
		try {
			return mFile.listFiles();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Ошибка чтания директории "
					+ mFile.getAbsolutePath(), "Предупреждение",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}
}
