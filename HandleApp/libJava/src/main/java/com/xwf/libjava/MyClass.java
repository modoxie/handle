package com.xwf.libjava;

public class MyClass {
    static CMDHelp cmdHelp;

    public static void main(String[] args) {
        cmdHelp = new CMDHelp();
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 30; i++) {
                try {
                    cmdHelp.tap(210, 1346);
                    Thread.sleep(200);
                    cmdHelp.tap(535, 1089);
                    Thread.sleep(220);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                cmdHelp.tap(85, 268);
                Thread.sleep(1000);
                cmdHelp.tap(874, 1143);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 20; i++) {
                try {
                    cmdHelp.tap(260, 1140);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                cmdHelp.tap(85, 268);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cmdHelp.destroy();
//        try {
//            BufferedImage img = PHash.cutImage("c:/fileImg/screenshot.png", 0, 490, 1080, 220);
//            BufferedImage t01 = ImageIO.read(new File("c:/fileImg/t01.png"));
//            System.out.println(PHash.compareImg(img, t01));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}
