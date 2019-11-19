package com.xwf.libjava;

import java.io.IOException;

public class CMDHelp {
    Process process;

    public void tap(int x, int y) {
        try {
            process = Runtime.getRuntime().exec("cmd /c adb shell input tap " + x + " " + y);
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

    public void getScreenshot() {
        try {
            process = Runtime.getRuntime().exec("cmd /c adb shell /system/bin/screencap -p /sdcard/screenshot.png");
            Thread.sleep(800);
            process = Runtime.getRuntime().exec("cmd /c adb pull /sdcard/screenshot.png /fileImg/screenshot.png");
            Thread.sleep(800);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
