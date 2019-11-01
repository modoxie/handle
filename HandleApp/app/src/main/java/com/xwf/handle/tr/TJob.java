package com.xwf.handle.tr;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class TJob {
    private Socket s;
    private static TJob instance;
    LinkedList<byte[]> data;
    boolean isRun;

    private TJob() {
        data = new LinkedList<>();
    }

    public synchronized static TJob getInstance() {
        if (instance == null) {
            instance = new TJob();
        }
        return instance;
    }

    public void start(Socket s) {
        this.s = s;
        isRun = true;
        new Thread(() -> {
            while (isRun) {
                if (data.size() > 0) {
                    sendData();
                }
            }
        }).start();

    }

    public void stop() {
        isRun = false;
        try {
            if (s != null) {
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData() {
        if (s == null || s.isClosed()) {
            return;
        }
        try {
            OutputStream outputStream = s.getOutputStream();
            byte[] bytes = data.pollFirst();
            if (bytes != null) {
                outputStream.write(bytes);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(byte what, int x, int y, int z) {
        byte[] bytes = new byte[13];
        bytes[0] = what;
        bytesAddInt(bytes, x, 1);
        bytesAddInt(bytes, y, 5);
        bytesAddInt(bytes, z, 9);
        data.addLast(bytes);
    }

    public static void bytesAddInt(byte[] bytes, int integer, int index) {
        for (int n = 0; n < 4; n++)
            bytes[3 - n + index] = (byte) (integer >> (n * 8));
    }
}
