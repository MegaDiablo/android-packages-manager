package ru.ucoz.megadiablo.android.apm.ui.touchpad;

import ru.ucoz.megadiablo.android.apm.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class TouchPad extends JDialog {

    private Core mCode;

    private JPanel contentPane;
    private JButton buttonRefresh;
    private ImagePanel touchPad;

    private Point mPointStartSwipe = null;

    public TouchPad(final Core pCore) {
        mCode = pCore;

        setTitle("Тачпад");
        setMinimumSize(new Dimension(640, 480));

        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonRefresh);

        touchPad.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point touch = transformPositionToScreen(e.getPoint());
                mCode.sendTap(touch.x, touch.y);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON2) {
                    mPointStartSwipe = transformPositionToScreen(e.getPoint());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (mPointStartSwipe != null && e.getButton() == MouseEvent.BUTTON2) {
                    Point from = mPointStartSwipe,
                            to = transformPositionToScreen(e.getPoint());
                    mCode.sendSwipe(from.x, from.y, to.x, to.y);
                }
                mPointStartSwipe = null;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        buttonRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
    }

    private Point transformPositionToScreen(Point position) {
        int imageWidth = touchPad.getWidth(), imageHeight = touchPad.getHeight();
        if (touchPad.image != null) {
            imageWidth = touchPad.image.getWidth();
            imageHeight = touchPad.image.getHeight();
        }
        return new Point(
                (int) Math.round((position.x * 1.0 / touchPad.getWidth()) * imageWidth),
                (int) Math.round((position.y * 1.0 / touchPad.getHeight()) * imageHeight)
        );
    }

    public void refresh() {
        try {
            File screenshot = File.createTempFile("touchpad-screenshot-", ".png");
            mCode.takeScreenshot(screenshot.getAbsolutePath(), new Runnable() {
                @Override
                public void run() {
                    touchPad.reload(screenshot.getAbsolutePath());
                    screenshot.deleteOnExit();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
