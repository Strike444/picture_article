package ru.andreyshimanovich.picture_article;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Andrey Shimanovich on 11.06.2016.
 */
public class ResizeJpg {
    private String[] masJpg;
    public ResizeJpg(String[] masJpg) throws IOException {
        this.masJpg = masJpg;
        resize(this.masJpg);
    }
    private void resize(String[] masJpg) throws IOException {
        for (String s: masJpg ) {
            File file = new File(s);
            long lm = file.lastModified();
            System.out.println(lm);
            BufferedImage im = ImageIO.read(file);
            int scaleW = im.getWidth();
            System.out.println("Ширина картинки: "  + scaleW);
            int scaleH = im.getHeight();
            System.out.println("Высота картинки: " + scaleH);
            int newHmin = 150;
            int newWmin = (150 * scaleW) / scaleH;
            int newW = 1024;
            int newH = (1024 * scaleH) / scaleW;
            System.out.println("Новая ширина для миниатюры: " + newWmin + "\n" + "Новая высота для миниатюры: " + newHmin);
            System.out.println("Новая ширина: " + newW + "\n" + "Новая высота: " + newH);
//            Image scaledInstance = im.getScaledInstance(800,600,1);
//            File output = new File(file.getPath() + "111" + file.getName());
//            ImageIO.write(im, "JPG", output);

            //TODO Отсортировать от наименьшего к наибольшему и переименовать.
//            BufferedImage im = new BufferedImage(3000, 2000, BufferedImage.TYPE_INT_ARGB);
//            OutputStream output = new FileOutputStream("resize" + s);
//            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(output);
//            JPEGEncodeParam jpgEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(im);
//            jpgEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
//            jpgEncodeParam.setXDensity(92);
//            jpgEncodeParam.setYDensity(92);
//            jpgEncodeParam.setQuality( 0.8F , false);
//            jpegEncoder.encode(im, jpgEncodeParam);
        }
    }
}
