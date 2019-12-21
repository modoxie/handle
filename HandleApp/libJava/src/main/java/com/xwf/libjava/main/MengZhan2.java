package com.xwf.libjava.main;

import com.xwf.libjava.img.FingerPrint;
import com.xwf.libjava.tools.PcTool;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MengZhan2 {
    public static void main(String[] args) {
        String path = "E:/git/Handle/HandleApp/libJava/src/main/res/drawable/";

        int num = 1000;
        while (num > 0) {
            try {
                BufferedImage i10 = PcTool.screenshot(1244, 774, 175, 60);
                File f = new File(path + "t01.png");
                if (!f.exists()) {
                    ImageIO.write(i10, "png", f);
                }
                BufferedImage i1 = ImageIO.read(f);
                float r = FingerPrint.compare(i10, i1);
                if (r > 0.95) {
                    PcTool.tap(1330, 800);
                    continue;
                }
                BufferedImage i20 = PcTool.screenshot(1423, 786, 137, 135);
                f = new File(path + "t02.png");
                if (!f.exists()) {
                    ImageIO.write(i20, "png", f);
                }
                BufferedImage i2 = ImageIO.read(f);
                r = FingerPrint.compare(i20, i2);
                if (r > 0.95) {
                    PcTool.tap(1489, 848);
                    continue;
                }

                BufferedImage i30 = PcTool.screenshot(640, 415, 560, 302);
                f = new File(path + "t03.png");
                if (!f.exists()) {
                    ImageIO.write(i30, "png", f);
                }
                BufferedImage i3 = ImageIO.read(f);
                r = FingerPrint.compare(i30, i3);
                if (r > 0.95) {
                    PcTool.tap(1028, 660);
                    num--;
                    System.out.println(num);
                    Thread.sleep(1000);
                    continue;
                }
                BufferedImage i40 = PcTool.screenshot(290, 170, 230, 20);
                f = new File(path + "t04.png");
                if (!f.exists()) {
                    ImageIO.write(i40, "png", f);
                }
                BufferedImage i4 = ImageIO.read(f);
                r = FingerPrint.compare(i40, i4);
                if (r > 0.95) {
                    PcTool.tap(1480, 840);
                    Thread.sleep(1000);
                }
            } catch (IOException | AWTException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
