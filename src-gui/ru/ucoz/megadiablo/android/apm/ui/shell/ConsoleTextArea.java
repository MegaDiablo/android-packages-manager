/**
 *
 */
package ru.ucoz.megadiablo.android.apm.ui.shell;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * @author Vladimir Baraznovsky
 *
 */
public class ConsoleTextArea extends JTextArea {

	private static final int ESC_CODE = 27;
	private int mPossition = 0;
	private int mLineOffset = 0;
	private boolean mEscMode = false;
	private StringBuilder mEscString = new StringBuilder();

	static boolean iShowCharacter(final char c) {
		int type = Character.getType(c);
		return Character.isLetterOrDigit(c) || Character.isSpaceChar(c)
				|| Character.isWhitespace(c) || (type == Character.MATH_SYMBOL)
				|| (type == Character.CURRENCY_SYMBOL)
				|| (type == Character.MODIFIER_SYMBOL)
				|| (type == Character.LETTER_NUMBER)
				|| (type == Character.OTHER_NUMBER)
				|| (type == Character.OTHER_LETTER)
				|| (type == Character.LINE_SEPARATOR)
				|| (type == Character.DASH_PUNCTUATION)
				|| (type == Character.START_PUNCTUATION)
				|| (type == Character.END_PUNCTUATION)
				|| (type == Character.CONNECTOR_PUNCTUATION)
				|| (type == Character.OTHER_PUNCTUATION)
				|| (type == Character.INITIAL_QUOTE_PUNCTUATION)
				|| (type == Character.FINAL_QUOTE_PUNCTUATION);

	}

	public void addCharacter(final char c) throws BadLocationException {
		Document doc = getDocument();
		if (mEscMode) {
			addCharacterEskMode(c);
			return;
		}
		if (c == ESC_CODE) {
			mEscMode = true;
		} else if (c == '\b') {
			if (mPossition > mLineOffset) {
				mPossition--;
			}
		} else if (c == '\r') {
			mPossition = mLineOffset;
		} else if (c == '\n') {
			doc.insertString(doc.getLength(), "\n", null);
			mLineOffset = doc.getLength();
			mPossition = mLineOffset;
		} else if (Character.isDefined(c) && ConsoleTextArea.iShowCharacter(c)) {
			if (mPossition < doc.getLength()) {
				doc.remove(mPossition, 1);
			}
			doc.insertString(mPossition, String.valueOf(c), null);
			mPossition++;
		} else {
			System.out
					.println(c + "=" + (int) (c) + "-" + Character.getType(c));

		}
	}

	private void addCharacterEskMode(final char c) throws BadLocationException {
		// System.out.println(c + "=" + (int) (c));
		mEscString.append(c);
		if (Character.isLetter(c)) {
			String command = mEscString.toString();
			execEscCommand(command);
			mEscMode = false;
			mEscString = new StringBuilder();
		}

	}

	/**
	 * Выболнение Esc комманд
	 *
	 * @see <a
	 *      href="http://en.wikipedia.org/wiki/ANSI_escape_code">http://en.wikipedia.org/wiki/ANSI_escape_code</a>
	 * @see <a
	 *      href="http://ascii-table.com/ansi-escape-sequences-vt-100.php">http://ascii-table.com/ansi-escape-sequences-vt-100.php</a>
	 *
	 */
	private void execEscCommand(final String command)
			throws BadLocationException {
		Document doc = getDocument();
		if (command.matches("\\[\\d+G")) {
			int value = getNumberFromEsc(command);
			mPossition = mLineOffset + value;
		} else if (command.matches("\\[\\d+K")) {
			int value = getNumberFromEsc(command);
			int count = value;
			if (value == 0) {
				count = doc.getLength() - mPossition;
			}
			doc.remove(mPossition, count);
		} else if (command.matches("\\[\\d+C")) {
			int value = getNumberFromEsc(command);
			mPossition = mPossition + value;
		} else {
			System.out.println("esc=" + command);
		}
	}

	private int getNumberFromEsc(final String command) {
		String strNumber = command.substring(1, command.length() - 1);
		int value = Integer.parseInt(strNumber);
		return value;
	}

	public void updateCaretPosition() {
		setCaretPosition(mPossition);
	}

}
