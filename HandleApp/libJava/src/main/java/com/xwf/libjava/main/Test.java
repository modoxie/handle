package com.xwf.libjava.main;

import com.xwf.libjava.CMDHelp;
import com.xwf.libjava.img.FingerPrint;
import com.xwf.libjava.tools.PcTool;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Test {
    public static void main(String[] args) {
        String path = "E:/git/Handle/HandleApp/libJava/src/main/res/drawable/";
        try {
            int num = 34;
            int i = 0;
            while (num-- > 0) {
                if (i == 6) {
                    PcTool.tap(1932, 51);
                    Thread.sleep(500);
                    PcTool.tap(985, 676);
                    i = 0;
                } else {
                    i++;
                }
                while (true) {
                    try {
                        BufferedImage i10 = PcTool.screenshot(0, 0, 120, 120);
                        File f = new File(path + "t01.png");
                        if (!f.exists()) {
                            ImageIO.write(i10, "png", f);
                        }
                        BufferedImage i1 = ImageIO.read(f);
                        float r = FingerPrint.compare(i10, i1);
                        System.out.println("r:" + r);
                        if (r > 0.95) {
                            PcTool.tap(1760, 900);
                            continue;
                        }
                        BufferedImage i20 = PcTool.screenshot(0, 0, 120, 120);
                        f = new File(path + "t02.png");
                        if (!f.exists()) {
                            ImageIO.write(i20, "png", f);
                        }
                        BufferedImage i2 = ImageIO.read(f);
                        r = FingerPrint.compare(i20, i2);
                        System.out.println("r:" + r);
                        if (r > 0.95) {
                            PcTool.tap(2178, 940);
                            continue;
                        }

                        BufferedImage i30 = PcTool.screenshot(0, 0, 120, 120);
                        f = new File(path + "t03.png");
                        if (!f.exists()) {
                            ImageIO.write(i30, "png", f);
                        }
                        BufferedImage i3 = ImageIO.read(f);
                        r = FingerPrint.compare(i30, i3);
                        System.out.println("r:" + r);
                        if (r > 0.95) {
                            PcTool.tap(1328, 684);
                            break;
                        }
                        PcTool.tap(2172, 930);
                    } catch (IOException | AWTException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (InterruptedException | AWTException e) {
            e.printStackTrace();
        }
    }
}
