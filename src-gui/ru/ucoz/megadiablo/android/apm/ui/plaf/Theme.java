package ru.ucoz.megadiablo.android.apm.ui.plaf;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Alexander Gromyko
 */
public class Theme {
	private LookAndFeelTheme mParent;
	private String mName;
	private String mMethodTheme;
	private String mValue;

	public LookAndFeelTheme getParent() {
		return mParent;
	}

	public void setParent(LookAndFeelTheme pParent) {
		mParent = pParent;
	}

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public String getMethodTheme() {
		return mMethodTheme;
	}

	public void setMethodTheme(String pMethodTheme) {
		mMethodTheme = pMethodTheme;
	}

	public String getValue() {
		return mValue;
	}

	public void setValue(String pValue) {
		mValue = pValue;
	}

	public String getKey() {
		StringBuilder sb = new StringBuilder();
		sb.append(mParent.getName());
		sb.append(":");
		sb.append(mParent.getClassName());
		sb.append("[");
		sb.append(mName);
		sb.append(":");
		sb.append(mMethodTheme);
		sb.append(":");
		sb.append(mValue);
		sb.append("]");

		return toSHA1(sb.toString());
	}

	private static String toSHA1(String convertme) {
		return toSHA1(convertme.getBytes());
	}

	private static String toSHA1(byte[] convertme) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return byteArrayToHexString(md.digest(convertme));
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
		}
		return result.toString();
	}
}
