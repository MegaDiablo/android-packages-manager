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
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

public class Shell extends JDialog implements Runnable {

	private static final int CHAR_BUFFER_SIZE = 1024;
	private final JScrollPane mContentScroll = new JScrollPane();
	private Process mExec;
	private InputStream mInputStream;
	private OutputStream mOutputStream;
	private OutputStreamWriter mWriter;
	private ConsoleTextArea mTextPane;
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
		mContentScroll.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(mContentScroll, BorderLayout.CENTER);
		{
			mTextPane = new ConsoleTextArea();
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
				mWriter = new OutputStreamWriter(mOutputStream);
				mReader = new InputStreamReader(mInputStream);

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			mTextPane.addKeyListener(new KeyAdapter() {

				@Override
				public void keyTyped(final KeyEvent pE) {
					pE.consume();
				}

				@Override
				public void keyReleased(final KeyEvent pE) {
					pE.consume();
				}

				@Override
				public void keyPressed(final KeyEvent e) {
					char c = e.getKeyChar();
					if (mWriter != null) {
						try {
							if (e.getKeyCode() == 127) {
								//delete
								pushKeys((char) 27, '[', 'C' ,c);
							} else if (e.getKeyCode() == 37) {
								//left
								pushKeys((char) 27, '[', 'D' );
							} else if (e.getKeyCode() == 39) {
								//right
								pushKeys((char) 27, '[', 'C' );
							} else if (e.getKeyCode() == 35) {
								//end
								pushKeys((char) 27, '[', 'F' );
							}  else if (e.getKeyCode() == 36) {
								//home
								pushKeys((char) 27, '[', 'H' );
							}  else if (Character.isDefined(c)) {
								pushKeys(c);
							} else {
								System.out.println(String.format(
										"bad key '%s'= %s",
										e.getKeyChar(),
										e.getKeyCode()));
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					e.consume();
				}


			});

			mContentScroll.setViewportView(mTextPane);

			Thread thread = new Thread(this);
			thread.setDaemon(true);
			thread.start();
			mTextPane.setCaretColor(mTextPane.getForeground());

		}
	}

	public void pushKeys(final char... ds) throws IOException {
		mWriter.write(ds);
		mWriter.flush();
	}
	public void close() {
		if (mExec != null) {
			mExec.destroy();
		}
	}

	@Override
	public void run() {
		System.out.println(mReader.getEncoding());
		try {
			char[] buffer = new char[CHAR_BUFFER_SIZE];
			int len = 0;
			while ((len = mReader.read(buffer)) > 0) {

				for (int i = 0; i < len; i++) {
					char c = buffer[i];
					mTextPane.addCharacter(c);
				}
				mTextPane.updateCaretPosition();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}
}
