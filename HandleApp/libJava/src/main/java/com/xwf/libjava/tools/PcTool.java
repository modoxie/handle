package com.xwf.libjava.tools;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class PcTool {
    public static BufferedImage screenshot(int x, int y, int width, int height) throws AWTException {
        return new Robot().createScreenCapture(new Rectangle(x, y, width, height));
    }

    public static void tap(int x, int y) throws AWTException {
        Robot robot = new Robot();
        robot.mouseMove(x, y);
        robot.mousePress(KeyEvent.BUTTON1_MASK);
        robot.mouseRelease(KeyEvent.BUTTON1_MASK);
    }
}
