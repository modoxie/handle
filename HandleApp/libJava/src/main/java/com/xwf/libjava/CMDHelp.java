package com.xwf.libjava;

import java.io.IOException;

public class CMDHelp {
    Process process;

    public void tap(int x, int y) {
        try {
            process = Runtime.getRuntime().exec("adb shell input tap " + x + " " + y);
            Thread.sleep(100);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        if (process != null) {
            process.destroy();
        }
    }

    public void getScreenshot(String path) {
        try {
            process = Runtime.getRuntime().exec("adb shell rm /sdcard/screenshot.png");
            Thread.sleep(100);
            process = Runtime.getRuntime().exec("adb shell /system/bin/screencap -p /sdcard/screenshot.png");
            Thread.sleep(2000);
            process = Runtime.getRuntime().exec("adb pull /sdcard/screenshot.png " + path);
            Thread.sleep(400);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
