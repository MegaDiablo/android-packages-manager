package ru.ucoz.megadiablo.android.apm.help;

import org.markdown4j.Markdown4jProcessor;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Gromyko
 */
public class HyperlinkListener implements javax.swing.event.HyperlinkListener {

	private final static boolean DEBUG = false;

	private Markdown4jProcessor mMarkdown4jProcessor;
	private JEditorPane mSource = null;
	private List<String> mBaseDirectories;

	public HyperlinkListener() {
		mMarkdown4jProcessor = new Markdown4jProcessor();
		mBaseDirectories = new ArrayList<String>();
		mBaseDirectories.add("");
	}

	public void setSource(JEditorPane pSource) {
		mSource = pSource;
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent pHyperlinkEvent) {
		if (pHyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {

			Object source = mSource;
			if (source == null) {
				source = pHyperlinkEvent.getSource();
			}

			JEditorPane pane = null;
			if (source instanceof JEditorPane) {
				pane = ((JEditorPane) source);
			}

			String anchor = "";
			String desc = pHyperlinkEvent.getDescription();

			int separator = desc.indexOf('#');
			if (separator != -1) {
				anchor = desc.substring(separator);
				desc = desc.substring(0, separator);
			}

			if (pane != null && desc.endsWith(".md")) {
				pane.setText(openMarkdown(desc));
				pane.setCaretPosition(0);
			}

			if (pane != null && anchor.startsWith("#")) {
				final String anchorScroll = anchor.substring(1);
				final JEditorPane paneScroll = pane;

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						paneScroll.scrollToReference(anchorScroll);
					}
				});
			}
		}
	}

	public String openMarkdown(String pFile) {
		File file = findFile(pFile);

		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append("<link rel=\"stylesheet\" href=\"file:doc/wiki.css\" type=\"text/css\" />");
		sb.append("<style>");
		printTheme(sb);
		sb.append("</style>");
		sb.append("</head><body>");
		try {
			sb.append(mMarkdown4jProcessor.process(file));
		} catch (IOException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
		}
		sb.append("</body></html>");

		if (DEBUG) {
			System.out.print(sb.toString());
		}

		return sb.toString();
	}

	public void addBaseDirectory(String pDirectory) {
		mBaseDirectories.add(pDirectory);
	}

	private File findFile(String pFile) {
		for (String dir : mBaseDirectories) {
			File file = new File(dir, pFile);
			if (file.exists() && file.isFile()) {
				return file;
			}
		}

		return new File(pFile);
	}

	private void printTheme(StringBuilder out) {
		out.append("body {");
		printItemColor(out, "background-color", "Panel.background");
		printItemColor(out, "color", "Panel.foreground");
		out.append("}\n");
		out.append("a {");
		printItemColor(out, "color", "RadioButtonMenuItem.acceleratorForeground");
		out.append("}\n");

		out.append(".anchor { ");
		printItemColor(out, "color", "RadioButtonMenuItem.disabledForeground");
		out.append("}\n");

		out.append("h1 { font-size: 19px; margin: .15em 1em 0.5em 0 }\n");
		out.append("h2 { font-size: 16px }\n");
		out.append("h3 { font-size: 14px }\n");

		out.append("body, th, tr { ");
		out.append("	font: normal 13px Verdana,Arial,'Bitstream Vera Sans',Helvetica,sans-serif; ");
		out.append("}\n");
	}

	private void printItemColor(StringBuilder out, String pCssField, String pSwingKey) {
		out.append(" ");
		out.append(pCssField);
		out.append(": ");
		out.append(transferColor(UIManager.getColor(pSwingKey)));
		out.append("; ");
	}

	private String transferColor(Color pColor) {
		StringBuilder color = new StringBuilder("#");

		String red = Integer.toHexString(pColor.getRed());
		if (red.length() < 1) {
			color.append('0');
		}
		color.append(red);

		String green = Integer.toHexString(pColor.getGreen());
		if (green.length() < 1) {
			color.append('0');
		}
		color.append(green);

		String blue = Integer.toHexString(pColor.getBlue());
		if (blue.length() < 1) {
			color.append('0');
		}
		color.append(blue);

		return color.toString();
	}
}
