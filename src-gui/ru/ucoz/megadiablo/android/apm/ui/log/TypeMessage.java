package ru.ucoz.megadiablo.android.apm.ui.log;

import java.awt.Color;

public enum TypeMessage {
	DEBUG(Color.BLUE), INFO(Color.GRAY), ERROR(Color.RED);
	private final Color mColor;



	private TypeMessage(final Color pColor) {
		mColor = pColor;
	}



	public Color getColor() {
		return mColor;
	}
}
