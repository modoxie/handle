package com.xwf.libjava.img;

import net.sourceforge.tess4j.util.ImageHelper;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class PHash {
    /**
     * String phash1 = toPhash(ImageIO.read(new File("C:/Users/dell/Desktop/11.png")));
     *         String phash2 = toPhash(ImageIO.read(new File("C:/Users/dell/Desktop/12.png")));
     *         System.out.println(phash1);
     *         System.out.println(phash2);
     *         int r1 =compareFingerPrint(phash1,phash2);
     */

    /**
     * 缩小图片
     *
     * @param image
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage reduceSize(BufferedImage image, int width, int height) {
        BufferedImage new_image = null;
        double width_times = (double) width / image.getWidth();
        double height_times = (double) height / image.getHeight();
        if (image.getType() == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = image.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            new_image = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            new_image = new BufferedImage(width, height, image.getType());
        }
        Graphics2D g = new_image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(image, AffineTransform.getScaleInstance(width_times, height_times));
        g.dispose();
        return new_image;
    }

    /**
     * 得到灰度值
     *
     * @param image
     * @return
     */
    public static double[][] getGrayValue(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[][] pixels = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = computeGrayValue(image.getRGB(i, j));
            }
        }
        return pixels;
    }

    /**
     * 计算灰度值
     *
     * @param pixel
     * @return
     */
    public static double computeGrayValue(int pixel) {
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = (pixel) & 255;
        return 0.3 * red + 0.59 * green + 0.11 * blue;
    }

    public static int avgImage(int[][] smallImage) {
        int avg = -1;
        int sum = 0;
        int count = 0;
        for (int i = 0; i < smallImage.length; i++) {
            for (int j = 0; j < smallImage[i].length; j++) {
                sum += smallImage[i][j];
                count++;
            }
        }
        avg = sum / count;
        return avg;
    }

    public static String to64(int avg, int[][] smallImage) {
        String result = "";
        for (int i = 0; i < smallImage.length; i++) {
            for (int j = 0; j < smallImage[i].length; j++) {
                if (smallImage[i][j] > avg) {
                    result += "1";
                } else {
                    result += "0";
                }
            }
        }
        return result;
    }

    //越小越相似
    public static int compareFingerPrint(String orgin_fingerprint, String compared_fingerprint) {
        int count = 0;
        for (int i = 0; i < orgin_fingerprint.length(); i++) {
            if (orgin_fingerprint.charAt(i) != compared_fingerprint.charAt(i)) {
                count++;
            }
        }
        return count;
    }


    public static String toPhash(BufferedImage image) {
        //缩小图片
        BufferedImage newImage = reduceSize(image, 32, 32);
        //转换为256位灰度
        double[][] pixels = getGrayValue(image);
        //计算DCT
        DCT dct = new DCT(25);
        int[][] tempDCT = dct.forwardDCT(pixels);
        //缩小DCT
        int[][] smallImage = dct.dequantitizeImage(tempDCT, false);
        //计算平均值
        int avg = avgImage(smallImage);
        //进一步减小DCT,得到信息指纹
        String result = to64(avg, smallImage);
        return result;
    }

    public static BufferedImage cutImage(String file, int x,
                                         int y, int width, int height) throws IOException {
        ImageInputStream imageStream = null;
        try {
            String imageType = (file.toLowerCase().endsWith(".png")) ? "png" : "jpg";
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageType);
            ImageReader reader = readers.next();
            imageStream = ImageIO.createImageInputStream(new FileInputStream(file));
            reader.setInput(imageStream, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            return reader.read(0, param);
        } finally {
            imageStream.close();
        }
    }

    public static int compareImg(BufferedImage img1, BufferedImage img2) {
        return compareFingerPrint(toPhash(img1), toPhash(img2));
    }

    public static BufferedImage convertImage(BufferedImage image){
        //按指定宽高创建一个图像副本
        image = ImageHelper.getSubImage(image, 0, 0, image.getWidth(), image.getHeight());
        //图像转换成灰度的简单方法 - 黑白处理
        image = ImageHelper.convertImageToGrayscale(image);
        //图像缩放 - 放大n倍图像
        image = ImageHelper.getScaledInstance(image, image.getWidth() * 3,   image.getHeight() * 3);
        return image;
    }
}
