package ru.ucoz.megadiablo.android.apm.ui;

import javax.swing.LookAndFeel;

/**
 * @author MegaDiablo
 * */
public enum EnumPLAF {

	AERO("Aero", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new com.jtattoo.plaf.aero.AeroLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme(DEFAULT);
		}
	},
	HIFI("HiFi", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new com.jtattoo.plaf.hifi.HiFiLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme(DEFAULT);
		}
	},
	ALUMINIUM("Aluminium", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new com.jtattoo.plaf.aluminium.AluminiumLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme(DEFAULT);
		}
	},
	FAST("Fast", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new com.jtattoo.plaf.fast.FastLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.fast.FastLookAndFeel.setTheme(DEFAULT);
		}
	},
	MCWIN("McWin", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new com.jtattoo.plaf.mcwin.McWinLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme(DEFAULT);
		}
	},
	MINT("Mint", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new com.jtattoo.plaf.mint.MintLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.mint.MintLookAndFeel.setTheme(DEFAULT);
		}
	},
	NOIRE("Noire", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new com.jtattoo.plaf.noire.NoireLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.noire.NoireLookAndFeel.setTheme(DEFAULT);
		}
	},
	SMART("Smart", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new com.jtattoo.plaf.smart.SmartLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.smart.SmartLookAndFeel.setTheme(DEFAULT);
		}
	},
	ACRY("Acry", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new com.jtattoo.plaf.acryl.AcrylLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}

		@Override
		public void setThemeDefault() {
			com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme(DEFAULT);
		}
	},
	METAL("Metal", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new javax.swing.plaf.metal.MetalLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}
	},
	MOTIF("Motif", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new com.sun.java.swing.plaf.motif.MotifLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}
	},
	NIMBUS("Nimbus", null) {
		private final LookAndFeel LOOK_AND_FEEL =
				new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}
	},
	WINDOWS("Windows", "win") {
		private final LookAndFeel LOOK_AND_FEEL = 
				new com.sun.java.swing.plaf.windows.WindowsLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}
	},
	GTK("GTK", "nix") {
		private final LookAndFeel LOOK_AND_FEEL = 
				new com.sun.java.swing.plaf.gtk.GTKLookAndFeel();

		@Override
		public LookAndFeel getPLookAndFeel() {
			return LOOK_AND_FEEL;
		}
	};

	private String mText;
	private String mPlatform;

	public abstract LookAndFeel getPLookAndFeel();
	protected static final String DEFAULT = "Default";

	public String getName() {
		return toString();
	}
	
	public void setThemeDefault(){
	}

	private EnumPLAF() {
		this.mText = getName();
	}

	private EnumPLAF(final String pText, final String pPlatform) {
		this.mText = pText;
		this.mPlatform = pPlatform;
	}

	public String getText() {
		return this.mText;
	}
	
	public String getPlatform() {
		return this.mPlatform;
	}
	
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
