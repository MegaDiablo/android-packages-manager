package ru.ucoz.megadiablo.android.apm.ui.shell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Shell extends JDialog implements Runnable {

	private static final int CHAR_BUFFER_SIZE = 1024;
	private final JPanel contentPanel = new JPanel();
	private Process mExec;
	private InputStream mInputStream;
	private OutputStream mOutputStream;
	private OutputStreamWriter mWriter;
	private JTextArea mTextPane;
	private InputStreamReader mReader;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		try {
			Shell dialog = new Shell();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Shell() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		contentPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			mTextPane = new JTextArea();
			mTextPane.setAlignmentX(Component.LEFT_ALIGNMENT);
			mTextPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			mTextPane.setFont(new Font("Courier New", Font.PLAIN, 12));
			mTextPane.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(final FocusEvent e) {
					mTextPane.getCaret().setVisible(true);
				}
			});
			mTextPane.setWrapStyleWord(true);
			mTextPane.setLineWrap(true);
			mTextPane.setEditable(false);
			mTextPane.setFocusTraversalKeysEnabled(false);
			mTextPane.setFocusCycleRoot(false);
			mTextPane.setForeground(Color.LIGHT_GRAY);
			mTextPane.setBackground(Color.BLACK);
			try {
				mExec =
						Runtime
								.getRuntime()
								.exec(
										"/home/vbaraznovsky/android/android-sdk-linux/platform-tools/adb shell");
				mInputStream = mExec.getInputStream();
				mOutputStream = mExec.getOutputStream();
				mWriter = new OutputStreamWriter(mOutputStream,"ascii");
				mReader = new InputStreamReader(mInputStream,"ascii");

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			mTextPane.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(final KeyEvent e) {
					char c = e.getKeyChar();
					if (mWriter != null) {
						try {

							if (Character.isDefined(c)) {
								mWriter.write(new char[] { c });
								mWriter.flush();
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});

			contentPanel.add(mTextPane);

			Thread thread = new Thread(this);
			thread.setDaemon(true);
			thread.start();
			mTextPane.getCaret().setVisible(true);
			mTextPane.setCaretColor(mTextPane.getForeground());

		}
	}

	public void close() {
		if (mExec != null) {
			mExec.destroy();
		}
	}

	@Override
	public void run() {

		mTextPane.getCaret().setVisible(true);

		System.out.println(mReader.getEncoding());
		Document doc = mTextPane.getDocument();
		int pos = 0;
		int lineOffset = 0;
		try {
			char[] buffer = new char[CHAR_BUFFER_SIZE];
			int len = 0;
			while ((len = mReader.read(buffer)) > 0) {

				for (int i = 0; i < len; i++) {
					char c = buffer[i];
					System.out.println(c + "=" + (int) (c));
					if (c == '\b') {
						if (pos > lineOffset) {
							doc.remove(doc.getLength() - 1, 1);
							pos--;
						}
					} else if (c == '\r') {
						pos = lineOffset;
					} else if (c == '\n') {
						doc.insertString(doc.getLength(), "\n", null);
						lineOffset = doc.getLength();
						pos = lineOffset;
					} else if (Character.isDefined(c)
							&&( Character.isLetterOrDigit(c)
							|| Character.isJavaIdentifierPart(c)
							|| Character.isSpaceChar(c)
							|| Character.isWhitespace(c)
							|| (c==':'))
							) {
						if (pos < doc.getLength()) {
							doc.remove(pos, 1);
						}
						doc.insertString(pos, String.valueOf(c), null);
						pos++;
					} else {

					}
				}
				mTextPane.setCaretPosition(pos);
				mTextPane.getCaret().setVisible(true);
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
