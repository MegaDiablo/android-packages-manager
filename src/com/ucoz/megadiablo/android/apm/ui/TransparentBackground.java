package com.ucoz.megadiablo.android.apm.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;

/**
 * @author MegaDiablo
 * */
public class TransparentBackground extends JComponent {
	/**
	 *
	 */
	private static final long serialVersionUID = 8278228699835701037L;
	// private JFrame frame;
	private Image background;

	public TransparentBackground(Component frame) {
		// this.frame = frame;
		updateBackground();

		frame.addComponentListener(new TransparentComponentListener(this));
	}

	public void updateBackground() {
		try {
			Robot rbt = new Robot();
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dim = tk.getScreenSize();
			background = rbt.createScreenCapture(new Rectangle(0, 0, (int) dim
					.getWidth(), (int) dim.getHeight()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		Point pos = this.getLocationOnScreen();
		Point offset = new Point(-pos.x, -pos.y);
		g.drawImage(background, offset.x, offset.y, null);
	}

	private static class TransparentComponentListener implements
			ComponentListener {

		private TransparentBackground component;

		public TransparentComponentListener(TransparentBackground component) {
			this.component = component;
		}

		public void componentResized(ComponentEvent e) {
			// Component[] components = ((JFrame) e.getComponent())
			// .getContentPane().getComponents();
			// components[0].repaint();
			// for (Component item : components) {
			// if (item instanceof TransparentBackground) {
			// item.repaint();
			// }
			// }
			component.repaint();
		}

		public void componentMoved(ComponentEvent e) {
			componentResized(e);
		}

		public void componentShown(ComponentEvent e) {
			componentResized(e);
		}

		public void componentHidden(ComponentEvent e) {
			componentResized(e);
		}
	}

	// public static void main(String[] args) {
	// JFrame frame = new JFrame("Transparent Window");
	// frame.addComponentListener(new TransparentComponentListener());
	// TransparentBackground bg = new TransparentBackground(frame);
	// bg.setLayout(new BorderLayout());
	// JButton button = new JButton("This is a button");
	// bg.add("North", button);
	// JLabel label = new JLabel("This is a label");
	// bg.add("South", label);
	// frame.getContentPane().add("Center", bg);
	// frame.pack();
	// frame.setSize(200, 200);
	// frame.show();
	// }
}
