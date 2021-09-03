package ru.ucoz.megadiablo.android.apm.ui.touchpad;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    public BufferedImage image = null;

    public ImagePanel() {
    }

    public void reload(String from) {
        try {
            image = ImageIO.read(new File(from));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
        }
    }
}
