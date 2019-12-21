package com.xwf.libjava;

import com.xwf.libjava.img.FingerPrint;
import com.xwf.libjava.img.PHash;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MengZhan {
    static CMDHelp cmdHelp;

    public static void main(String[] args) {
        cmdHelp = new CMDHelp();
        String path = "E:/git/Handle/HandleApp/libJava/src/main/res/drawable/";
        try {
            int num = 34;
            int i = 0;
            while (num-- > 0) {
                if (i == 6) {
                    cmdHelp.tap(1932, 51);
                    Thread.sleep(500);
                    cmdHelp.tap(985, 676);
                    i = 0;
                } else {
                    i++;
                }
                while (true) {
                    try {
                        cmdHelp.getScreenshot(path + "screenshot.png");
                        BufferedImage i10 = PHash.cutImage(path + "screenshot.png", 1650, 855, 265, 110);
                        File f = new File(path + "t01.png");
                        if (!f.exists()) {
                            ImageIO.write(i10, "png", f);
                        }
                        BufferedImage i1 = ImageIO.read(f);
                        float r = FingerPrint.compare(i10, i1);
                        System.out.println("r:" + r);
                        if (r > 0.95) {
                            cmdHelp.tap(1760, 900);
                            continue;
                        }
                        BufferedImage i20 = PHash.cutImage(path + "screenshot.png", 2100, 872, 235, 203);
                        f = new File(path + "t02.png");
                        if (!f.exists()) {
                            ImageIO.write(i20, "png", f);
                        }
                        BufferedImage i2 = ImageIO.read(f);
                        r = FingerPrint.compare(i20, i2);
                        System.out.println("r:" + r);
                        if (r > 0.95) {
                            cmdHelp.tap(2178, 940);
                            continue;
                        }

                        BufferedImage i30 = PHash.cutImage(path + "screenshot.png", 830, 427, 780, 300);
                        f = new File(path + "t03.png");
                        if (!f.exists()) {
                            ImageIO.write(i30, "png", f);
                        }
                        BufferedImage i3 = ImageIO.read(f);
                        r = FingerPrint.compare(i30, i3);
                        System.out.println("r:" + r);
                        if (r > 0.95) {
                            cmdHelp.tap(1328, 684);
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cmdHelp.tap(2172, 930);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cmdHelp.destroy();
        }
    }


}
