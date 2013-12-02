/**
 *
 */
package ru.ucoz.megadiablo.android.apm.ui.shell;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class ConsoleUtils {

	private static Map<Integer, char[]> sCommandMap =
			new HashMap<Integer, char[]>();
	static {
		sCommandMap.put(KeyEvent.VK_INSERT, 	new char[] { 27, '[', '2', '~' });
		sCommandMap.put(KeyEvent.VK_DELETE, 	new char[] { 27, '[', '3', '~' });
		sCommandMap.put(KeyEvent.VK_PAGE_UP, 	new char[] { 27, '[', '5', '~' });
		sCommandMap.put(KeyEvent.VK_PAGE_DOWN, 	new char[] { 27, '[', '6', '~' });
		sCommandMap.put(KeyEvent.VK_UP, 		new char[] { 27, '[', 'A' });
		sCommandMap.put(KeyEvent.VK_DOWN, 		new char[] { 27, '[', 'B' });
		sCommandMap.put(KeyEvent.VK_RIGHT, 		new char[] { 27, '[', 'C' });
		sCommandMap.put(KeyEvent.VK_LEFT, 		new char[] { 27, '[', 'D' });
		sCommandMap.put(KeyEvent.VK_END, 		new char[] { 27, '[', 'F' });
		sCommandMap.put(KeyEvent.VK_HOME, 		new char[] { 27, '[', 'H' });

	}

	public static char[] getKeyCommand(final KeyEvent pEvent) {
		return sCommandMap.get(pEvent.getKeyCode());
	}
	public static boolean hasKeyCommand(final KeyEvent pEvent) {
		return sCommandMap.containsKey(pEvent.getKeyCode());
	}
}
