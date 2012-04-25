/**
 *
 */
package com.adbhelper.adb;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class AdbUtils {
	public static String getResultPattern(String string, String regExp) {
		Pattern pat = Pattern.compile(regExp);
		Matcher mat = pat.matcher(string);

		String result = "";
		if (mat.find()) {
			result = mat.group();

		}
		return result;

	}

	public static String[] getResultsPattern(String string, String regExp) {
		Pattern pat = Pattern.compile(regExp);
		Matcher mat = pat.matcher(string);

		String[] result = {};
		if (mat.find()) {
			result = new String[mat.groupCount()];
			for (int i = 0; i < result.length; i++) {
				result[i] = mat.group(i + 1);
			}

		}
		return result;
	}


	public static File generateTempFile(String prefix, String suffix){
		File result=null;
		try {
			result=File.createTempFile(prefix, suffix);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (result==null)
		{
			int rand=(int) (Math.random()*0xFFFFFFFF);
			result=new File(prefix+String.format("_%X", rand)+suffix);
		}
		return result;



	}
}
