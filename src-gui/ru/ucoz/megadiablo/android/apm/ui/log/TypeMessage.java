package ru.ucoz.megadiablo.android.apm.ui.log;

import java.awt.Color;

public enum TypeMessage {
	INFO(Color.DARK_GRAY), ERROR(Color.RED), DEBUG(Color.BLUE);
	private final Color mColor;



	private TypeMessage(final Color pColor) {
		mColor = pColor;
	}



	public Color getColor() {
		return mColor;
	}
}
