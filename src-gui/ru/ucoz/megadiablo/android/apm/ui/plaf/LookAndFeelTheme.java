package ru.ucoz.megadiablo.android.apm.ui.plaf;

import javax.swing.*;
import java.util.List;

/**
 * @author Alexander Gromyko
 */
public class LookAndFeelTheme {
	private String mName;
	private String mClassName;
	private Boolean mExist;

	private LookAndFeel mLookAndFeel;

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public String getClassName() {
		return mClassName;
	}

	public void setClassName(String pClassName) {
		mClassName = pClassName;
	}

	public LookAndFeel getLookAndFeel() {
		if (mLookAndFeel == null) {
			compile();
		}
		return mLookAndFeel;
	}

	public void compile() {
		try {
			Class class_ = ClassLoader.getSystemClassLoader().loadClass(mClassName);
			mLookAndFeel = (LookAndFeel) class_.newInstance();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public boolean exist() {
		if (mExist != null) {
			return mExist;
		}
		mExist = false;
		try {
			LookAndFeel laf = (LookAndFeel) ClassLoader.getSystemClassLoader().loadClass(mClassName).newInstance();
			mExist = laf.isSupportedLookAndFeel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mExist;
	}
}
