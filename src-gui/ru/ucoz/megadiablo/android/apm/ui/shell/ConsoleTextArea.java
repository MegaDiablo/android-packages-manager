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

	private int mPossition = 0;
	private int mLineOffset = 0;

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
		if (c == '\b') {
			if (mPossition > mLineOffset) {
				doc.remove(doc.getLength() - 1, 1);
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

	public void updateCaretPosition() {
		setCaretPosition(mPossition);
	}

}
