package ru.andreyshimanovich.picture_article;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
        final String OS = System.getProperty("os.name");
        int i = 1;
        for (String s : masJpg) {
            File file = new File(s);
            long lm = file.lastModified();
            System.out.println(lm);
            System.out.println(file.toPath().getParent());
            BufferedImage im = ImageIO.read(file);
            int scaleW = im.getWidth();
            System.out.println("Ширина картинки: " + scaleW);
            int scaleH = im.getHeight();
            System.out.println("Высота картинки: " + scaleH);
            int newHmin = 150;
            int newWmin = (150 * scaleW) / scaleH;
            int newW = 1024;
            int newH = (1024 * scaleH) / scaleW;
            System.out.println("Новая ширина для миниатюры: " + newWmin + "\n" + "Новая высота для миниатюры: " + newHmin);
            System.out.println("Новая ширина: " + newW + "\n" + "Новая высота: " + newH);

            BufferedImage scaled = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = scaled.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(im, 0, 0, newW, newH, null);
            g.dispose();

            if (OS.equals("Linux")) {
                if (file.getPath().toString().equals(file.getParent() + "/" + i + ".jpg")) {
                    System.out.println("* Найдено сопадение имен исходного " + file.getName() + " и выходного файлов " +
                    i + ".jpg");
                    System.out.println("Выполняю переименование исходного файла " + file.getName());
                    String ishFile = new String(file.getPath().toString().replaceAll(".jpg","_ish.jpg"));
                    System.out.println("тра ля ля " + ishFile);
                    file.renameTo(new File(ishFile));
                }
                ImageIO.write(scaled, "JPEG", new File(file.getParent() + "/" + i + ".jpg"));
            } else if (OS.matches("Windows.*")){
                ImageIO.write(scaled, "JPEG", new File(file.getParent() + "\\" + i + ".jpg"));
            }
            i++;

            //TODO Реализовать архивирование исходных файлов
//            Image scaledInstance = im.getScaledInstance(800,600,1);
//            File output = new File(file.getPath() + "111" + file.getName());
//            ImageIO.write(im, "JPG", output);

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
