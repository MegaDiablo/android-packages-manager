package ru.ucoz.megadiablo.android.apm.ui;

import javax.swing.LookAndFeel;

/**
 * @author MegaDiablo
 * */
public enum EnumPLAF {

	AERO {
		private final LookAndFeel LOOK_AND_FEEL = new com.jtattoo.plaf.aero.AeroLookAndFeel();

		@Override
		protected LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme(DEFAULT);
		}

		@Override
		public String getName() {
			return toString();
		}
	},
	HIFI {
		private final LookAndFeel LOOK_AND_FEEL = new com.jtattoo.plaf.hifi.HiFiLookAndFeel();

		@Override
		protected LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme(DEFAULT);
		}

		@Override
		public String getName() {
			return toString();
		}
	},
	ALUMINIUM {
		private final LookAndFeel LOOK_AND_FEEL = new com.jtattoo.plaf.aluminium.AluminiumLookAndFeel();

		@Override
		protected LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme(DEFAULT);
		}

		@Override
		public String getName() {
			return toString();
		}
	},
	FAST {
		private final LookAndFeel LOOK_AND_FEEL = new com.jtattoo.plaf.fast.FastLookAndFeel();

		@Override
		protected LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.fast.FastLookAndFeel.setTheme(DEFAULT);
		}

		@Override
		public String getName() {
			return toString();
		}
	},
	MCWIN {
		private final LookAndFeel LOOK_AND_FEEL = new com.jtattoo.plaf.mcwin.McWinLookAndFeel();

		@Override
		protected LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme(DEFAULT);
		}

		@Override
		public String getName() {
			return toString();
		}
	},
	MINT {
		private final LookAndFeel LOOK_AND_FEEL = new com.jtattoo.plaf.mint.MintLookAndFeel();

		@Override
		protected LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.mint.MintLookAndFeel.setTheme(DEFAULT);
		}

		@Override
		public String getName() {
			return toString();
		}
	},
	NOIRE {
		private final LookAndFeel LOOK_AND_FEEL = new com.jtattoo.plaf.noire.NoireLookAndFeel();

		@Override
		protected LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.noire.NoireLookAndFeel.setTheme(DEFAULT);
		}

		@Override
		public String getName() {
			return toString();
		}
	},
	SMART {
		private final LookAndFeel LOOK_AND_FEEL = new com.jtattoo.plaf.smart.SmartLookAndFeel();

		@Override
		protected LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.smart.SmartLookAndFeel.setTheme(DEFAULT);
		}

		@Override
		public String getName() {
			return toString();
		}
	},
	ACRY {
		private final LookAndFeel LOOK_AND_FEEL = new com.jtattoo.plaf.acryl.AcrylLookAndFeel();

		@Override
		protected LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme(DEFAULT);
		}

		@Override
		public String getName() {
			return toString();
		}
	};

	protected abstract LookAndFeel getPLookAndFeel();

	protected static final String DEFAULT = "Default";

	public abstract String getName();

	public LookAndFeel getLookAndFeel() {
		return getPLookAndFeel();
	}

	public abstract void setThemeDefault();

	public static EnumPLAF findByName(final String pName) {
		if (pName == null) {
			return null;
		}
		EnumPLAF[] plafs = values();
		for (EnumPLAF item : plafs) {
			if (pName.equals(item.getName())) {
				return item;
			}
		}
		return null;
	}

}
