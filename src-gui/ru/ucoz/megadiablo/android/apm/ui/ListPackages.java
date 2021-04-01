package ru.ucoz.megadiablo.android.apm.ui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ru.ucoz.megadiablo.android.apm.Core;
import ru.ucoz.megadiablo.android.apm.FileDrop;
import ru.ucoz.megadiablo.android.apm.iface.PackagesListener;
import ru.ucoz.megadiablo.android.apm.impl.PackagesListenerDefault;
import ru.ucoz.megadiablo.android.apm.ui.settings.Settings;

import com.adbhelper.adb.AdbPackage;

/**
 * @author MegaDiablo
 * */
public class ListPackages extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 6197850016233338650L;
	private JPopupMenu popupMenu;
	private JTable table;
	private DefaultTableModel model;
	private FilterByText mFilter = new FilterByText();
	private TableRowSorter<TableModel> sorter;

	private JFolderChoose mFolderChoose;

	private PackagesListener mPackagesListner;

	private Core mCore = null;
	private JMenuItem mMenuItemDownload;
	private JMenuItem mMenuItemDelete;
	private JMenuItem mMenuItemRun;
	private JMenuItem mMenuItemRefresh;
	private JMenuItem mMenuItemShellRunAs;
	private JSeparator separator;
	private JMenuItem mMenuItemClearData;
	private JMenuItem mMenuItemMonkey;
	private Settings mSettings;
	private JMenuItem mMenuItemInfo;

	public ListPackages(final Core pCore) {
		mCore = pCore;
		mSettings = Settings.getInstance();
		setLayout(new BorderLayout(0, 0));

		sorter = new TableRowSorter<TableModel>();

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setVerifyInputWhenFocusTarget(false);
		table.setRowSorter(sorter);

		updateModel(null);

		popupMenu = new JPopupMenu();

		{
			mMenuItemRun = new JMenuItem("Запустить");
			mMenuItemRun.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					startPackages(false);
				}
			});
			popupMenu.add(mMenuItemRun);
		}

		// {
		// mMenuItemRun = new JMenuItem("Отладка");
		// mMenuItemRun.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(final ActionEvent e) {
		// startPackages(true);
		// }
		// });
		// popupMenu.add(mMenuItemRun);
		// }

		{
			mMenuItemClearData = new JMenuItem("Очистить");
			mMenuItemClearData.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					clearDataPackages();
				}
			});
			mMenuItemClearData
			.setToolTipText("Позволяет очистить данные приложения");
			popupMenu.add(mMenuItemClearData);
		}

		{
			mMenuItemDelete = new JMenuItem("Удалить");
			mMenuItemDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					int result =
							JOptionPane.showConfirmDialog(
									ListPackages.this,
									"Точно хотите удалить приложение(я)?",
									"Предупреждение",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						removePackages();
					}
				}
			});
			popupMenu.add(mMenuItemDelete);
		}

		{
			mMenuItemDownload = new JMenuItem("Скачать");
			mMenuItemDownload.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					// int result = mChooseFolder
					// .showSaveDialog(ListPackages.this);

					if (mFolderChoose == null) {
						mFolderChoose = new JFolderChoose(null);
					}

					int result = mFolderChoose.open();

					if (result == JFileChooser.APPROVE_OPTION) {
						downloadPackages(mFolderChoose.getSelectedFolder());
					}
				}
			});
			popupMenu.add(mMenuItemDownload);
		}

		{
			mMenuItemInfo = new JMenuItem("Информация");
			mMenuItemInfo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					updateInformationPackages();
				}
			});
			mMenuItemInfo
					.setToolTipText("Позволяет обновить информацию о приложении");
			popupMenu.add(mMenuItemInfo);
		}

		{
			mMenuItemShellRunAs = new JMenuItem("Консоль приложения");
			mMenuItemShellRunAs.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					runShellRunAsPackages();
				}
			});
			mMenuItemShellRunAs
					.setToolTipText("Позволяет запустить консоль с правами приложения");
			popupMenu.add(mMenuItemShellRunAs);
		}

		{
			mMenuItemMonkey = new JMenuItem("Monkey");
			mMenuItemMonkey.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					monkeyPackages();
				}
			});
			mMenuItemMonkey
					.setToolTipText("Генерирует случайные события клавиатуры и экрана для приложения");
			popupMenu.add(mMenuItemMonkey);
		}
		{
			separator = new JSeparator();
			popupMenu.add(separator);
		}

		{
			mMenuItemRefresh = new JMenuItem("Обновить");
			mMenuItemRefresh.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					mCore.refreshPackages();
				}
			});

			popupMenu.add(mMenuItemRefresh);
		}

		MouseListener popupListener = new PopupListener();
		scrollPane.addMouseListener(popupListener);
		table.addMouseListener(popupListener);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					Point p = e.getPoint();
					int rowNumber = table.rowAtPoint(p);
					ListSelectionModel model = table.getSelectionModel();
					if (!model.isSelectedIndex(rowNumber)) {
						model.setSelectionInterval(rowNumber, rowNumber);
						// model.addSelectionInterval(rowNumber, rowNumber);
					}
					checkButtonEnabled();
				}
			}
		});

		scrollPane.setViewportView(table);

		new FileDrop(scrollPane, new FileDrop.Listener() {
			@Override
			public void filesDropped(final File[] files) {
				installPackages(files);
			}
		});

		initListeners();
	}

	private void initListeners() {
		if (mCore == null) {
			return;
		}

		mCore.removePackagesListener(mPackagesListner);

		mPackagesListner = new PackagesListenerDefault() {
			@Override
			public void updatePackages(final List<AdbPackage> pAdbPackages) {
				updateListPackages(pAdbPackages);
			};
		};

		mCore.addPackagesListener(mPackagesListner);
	}

	private void updateListPackages(final List<AdbPackage> packages) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				updateModel(packages);
			}
		});
	}

	public void setFilter(String text) {
		if (text == null) {
			text = "";
		}
		mFilter.text = text.toLowerCase();
		sorter.setRowFilter(mFilter);
	}

	private void updateModel(final List<AdbPackage> pList) {

		String[] columsNames = { "Пакет", "Название" };

		Object[][] data = {};
		if (pList != null) {
			data = new Object[pList.size()][columsNames.length + 1];

			for (int i = 0; i < pList.size(); i++) {
				AdbPackage item = pList.get(i);
				data[i][columsNames.length] = item;
				if (item != null) {
					data[i][0] = " - пакет не определен - ";
					if (item.getName() != null) {
						data[i][0] = item.getName();
					}

					data[i][1] = "";
					if (item.getLabel() != null) {
						data[i][1] = item.getLabel();
					}
				}

			}
		}

		model = new DefaultTableModel(data, columsNames) {

			private static final long serialVersionUID = 5795337809978978846L;

			@SuppressWarnings("rawtypes")
			private Vector nonNullVector(final Vector v) {
				if (v == null) {
					return new Vector();
				}
				return v;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public void setDataVector(final Vector dataVector,
					final Vector columnIdentifiers) {

				this.dataVector = nonNullVector(dataVector);
				this.columnIdentifiers = nonNullVector(columnIdentifiers);
				fireTableStructureChanged();
			}

			@Override
			public boolean isCellEditable(final int row, final int column) {
				return false;
			};
		};

		table.setModel(model);
		sorter.setModel(model);
	}

	private void checkButtonEnabled() {
		int sel = table.getSelectedRowCount();
		boolean deviceAvailable = mCore.getSelectDevice().isAvailable();
		boolean enabled = deviceAvailable && (sel > 0);

		mMenuItemRun.setEnabled(enabled);
		mMenuItemInfo.setEnabled(enabled);
		mMenuItemDelete.setEnabled(enabled);
		mMenuItemDownload.setEnabled(enabled);
		mMenuItemShellRunAs.setEnabled(enabled);
		mMenuItemMonkey.setEnabled(enabled);
		mMenuItemClearData.setEnabled(enabled);
		mMenuItemRefresh.setEnabled(deviceAvailable);

	}

	static class FilterByText extends RowFilter<TableModel, Integer> {

		private String text;

		@Override
		public boolean include(final javax.swing.RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {

			if (text == null) {
				return true;
			}

			int count = entry.getValueCount();
			for (int i = 0; i < count; i++) {
				String value = entry.getStringValue(i);
				if (value != null && value.toLowerCase().contains(text)) {
					return true;
				}
			}

			return false;
		}
	}

	class PopupListener extends MouseAdapter {
		@Override
		public void mousePressed(final MouseEvent e) {
			showPopup(e);
		}

		@Override
		public void mouseReleased(final MouseEvent e) {
			showPopup(e);
		}

		private void showPopup(final MouseEvent e) {
			if (e.isPopupTrigger()) {
				checkButtonEnabled();
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}

	}

	public void removePackages() {
		int[] selected = table.getSelectedRows();
		for (int index : selected) {
			removePackage(index);
		}

		mCore.refreshPackages();
	}

	public void updateInformationPackages() {
		int[] selected = table.getSelectedRows();
		for (int index : selected) {
			updateInformationPackage(index);
		}

		mCore.refreshPackages();
	}

	public void runShellRunAsPackages() {
		int[] selected = table.getSelectedRows();
		for (int index : selected) {
			runShellRunAsPackage(index);
		}

		mCore.refreshPackages();
	}

	public void startPackages(final boolean pDebug) {
		int[] selected = table.getSelectedRows();
		for (int index : selected) {
			startPackage(index, pDebug);
		}
	}
	public void monkeyPackages() {
		int[] selected = table.getSelectedRows();
		for (int index : selected) {
			monkeyPackage(index);
		}
	}


	public void clearDataPackages() {
		int[] selected = table.getSelectedRows();
		for (int index : selected) {
			clearDataPackage(index);
		}
	}

	public void downloadPackages(final String pTo) {
		String to = pTo;
		if (to != null && to.length() > 1 && !to.endsWith(File.separator)) {
			to += File.separator;
		}
		int[] selected = table.getSelectedRows();
		for (int index : selected) {
			downloadPackage(index, to);
		}
	}

	private boolean removePackage(final int index) {
		AdbPackage item = getAdbPackageByIndex(index);
		if (item != null) {
			mCore.uninstall(item);
		}
		return true;
	}

	private boolean startPackage(final int index, final boolean pDebug) {
		AdbPackage item = getAdbPackageByIndex(index);
		if (item != null) {
			mCore.startApp(item, pDebug);
		}
		return true;
	}

	private boolean monkeyPackage(final int index) {
		AdbPackage item = getAdbPackageByIndex(index);
		if (item != null) {
			int count = mSettings.getMonkeyCount();
			mCore.monkeyApp(item, count);
		}
		return true;
	}

	private boolean clearDataPackage(final int index) {
		AdbPackage item = getAdbPackageByIndex(index);
		if (item != null) {
			mCore.clearDataApp(item);
		}
		return true;
	}

	private boolean updateInformationPackage(final int index) {
		AdbPackage item = getAdbPackageByIndex(index);
		if (item != null) {
			mCore.updateInforamtionApplication(item);
		}
		return true;
	}

	private boolean runShellRunAsPackage(final int index) {
		AdbPackage item = getAdbPackageByIndex(index);
		if (item != null) {
			mCore.runShellRunAs(item);
		}
		return true;
	}

	private boolean downloadPackage(final int index, final String to) {
		AdbPackage item = getAdbPackageByIndex(index);
		if (item != null) {
			mCore.download(item, String.format("%s%s.apk", to, item));
		}
		return true;
	}

	private void installPackages(final File[] pFiles) {
		mCore.install(pFiles);
		// for (File file : pFiles) {
		// mCore.install(file);
		// }
		//
		// mCore.refreshPackages();
	}

	private AdbPackage getAdbPackageByIndex(final int pIndex) {
		int newIndex = table.convertRowIndexToModel(pIndex);

		Object obj = table.getModel().getValueAt(newIndex, 2);
		if (obj != null && obj instanceof AdbPackage) {
			AdbPackage item = (AdbPackage) obj;
			return item;
		}
		return null;
	}

}
