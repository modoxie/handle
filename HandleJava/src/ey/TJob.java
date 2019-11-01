package ey;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class TJob implements Runnable {
    private Robot robot;
    private Socket s;
    private Thread t;
    private boolean isRun = true;
    private LinkedList<Integer> keyPress;


    public TJob(Robot robot, Socket s) {
        this.robot = robot;
        this.s = s;
        isRun = true;
        keyPress = new LinkedList<>();
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = (int) screenSize.getWidth();
            int screenHeight = (int) screenSize.getHeight();
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            byte[] bytes = new byte[13];
            int key;
            while (isRun) {
                for (int i = 0; i < 13; i++) {
                    int read = in.read();
                    if (read == -1) {
                        i--;
                    } else {
                        bytes[i] = (byte) read;
                    }
                }
                switch (bytes[0]) {
                    case 1:
                        PointerInfo pinfo = MouseInfo.getPointerInfo();
                        if (pinfo != null) {
                            Point p = pinfo.getLocation();
                            int mx = (int) p.getX();
                            int my = (int) p.getY();
                            robot.mouseMove(mx + bytesToInt(bytes, 1) / 4, my + bytesToInt(bytes, 5) / 4);
                        }
                        break;
                    case 2:
                        robot.mouseMove(screenWidth / 2 + bytesToInt(bytes, 1), screenHeight / 2 + bytesToInt(bytes, 5));
                        robot.mousePress(MouseEvent.BUTTON1);
                        robot.mouseRelease(MouseEvent.BUTTON1);
                        break;
                    case 3:
                        key = bytesToInt(bytes, 1);
                        if (!keyPress.contains(key)) {
                            robot.keyPress(bytesToInt(bytes, 1));
                            keyPress.add(key);
                        }
                        break;
                    case 4:
                        key = bytesToInt(bytes, 1);
                        if (keyPress.contains(key)) {
                            robot.keyRelease(bytesToInt(bytes, 1));
                            keyPress.remove(Integer.valueOf(key));
                        }
                        break;
                    case 5:
                        robot.mouseMove(screenWidth / 2 + bytesToInt(bytes, 1), screenHeight / 2 + bytesToInt(bytes, 5));
                        robot.keyPress(bytesToInt(bytes, 9));
                        robot.keyRelease(bytesToInt(bytes, 9));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void stop() {
        isRun = false;
        keyPress.clear();
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }
}
