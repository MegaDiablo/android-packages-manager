package ru.ucoz.megadiablo.android.apm.ui.plaf;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Gromyko
 */
public class ThemeManager {
	private List<Theme> mThemes = new ArrayList<Theme>();

	public void load(final File pFile) throws IOException, ParseException {
		FileReader reader = new FileReader(pFile);
		JSONParser parser = new JSONParser();
		JSONArray lafs = (JSONArray) parser.parse(reader);
		for (Object objectLAF : lafs) {
			JSONObject item = (JSONObject) objectLAF;

			LookAndFeelTheme laf = new LookAndFeelTheme();
			laf.setName(String.class.cast(item.get("name")));
			laf.setClassName(String.class.cast(item.get("class")));

			JSONArray themes = (JSONArray) item.get("themes");
			for (Object objectTheme : themes) {
				JSONObject itemTheme = (JSONObject) objectTheme;

				Theme theme = new Theme();
				theme.setParent(laf);
				theme.setName(String.class.cast(item.get("name")));
				theme.setMethodTheme(String.class.cast(item.get("method")));
				theme.setValue(String.class.cast(item.get("value")));

				mThemes.add(theme);
			}
		}
	}

	public Theme getTheme(final String pKey) {
		if (pKey == null) {
			return null;
		}

		for (Theme theme : mThemes) {
			if (pKey.equals(theme.getKey())) {
				return theme;
			}
		}
		return null;
	}

	public static void main(String[] args) throws IOException, ParseException {
		new ThemeManager().load(new File("./resources/themes/jtatoo.thm"));
	}
}
