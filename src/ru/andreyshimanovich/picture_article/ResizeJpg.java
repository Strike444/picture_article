package ru.andreyshimanovich.picture_article;

import javafx.scene.input.DataFormat;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Andrey Shimanovich on 11.06.2016.
 */
public class ResizeJpg {
    private String[] masJpg;
    private final String OS = System.getProperty("os.name");
    private final String HOME = System.getProperty("user.home");

    public ResizeJpg(String[] masJpg) throws IOException {
        this.masJpg = masJpg;
        ArrayList<String> filesForArh = resize(this.masJpg);
        toZip(filesForArh);
        delAfterZip(masJpg);
    }

    private ArrayList<String> resize(String[] masJpg) throws IOException {
        int i = 1;
        ArrayList<String> masOldJpg = new ArrayList<String>();

        for (String s : masJpg) {
            File file = new File(s);
//            long lm = file.lastModified();
//            System.out.println(lm);
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
                    String ishFile = new String(file.getPath().toString().replaceAll(".jpg", "_ish.jpg"));
                    System.out.println("Исходный файл после переименования: " + ishFile);
                    masOldJpg.add(file.toString());
                }
                // Проверка на главыный файл
                if (file.getPath().toString().equals(file.getParent() + "/" + "glav.jpg")) {
                    System.out.println("Обнаружен главный файл: " + file.getPath());
                    ImageIO.write(scaled, "JPEG", new File(file.getPath()));
                    i--;
                } else {
                    System.out.println("Добовляю файл в список на архивацию и удаление" + file);
                    masOldJpg.add(file.toString());
                    ImageIO.write(scaled, "JPEG", new File(file.getParent() + "/" + i + ".jpg"));
                }
            } else if (OS.matches("Windows.*")) {
                ImageIO.write(scaled, "JPEG", new File(file.getParent() + "\\" + i + ".jpg"));
            }
            i++;
        }
        System.out.println("Длинна " + masOldJpg.size());
        return masOldJpg;
    }

    // Удаление файлов после архивации
    private void delAfterZip (String[] s) {
        for (int i = 0; i < s.length; i++) {
            File f = new File(s[i]);
            //TODO не канает. Удаляет файлы переименованные.
//            if (f.exists()) {
//                f.delete();
            }
        }
//    }

    private void toZip(ArrayList<String> al) throws IOException {
        if (OS.equals("Linux")) {
            System.out.println("Запускаю архивирование");
            FileOutputStream fos = new FileOutputStream(HOME + "/picture_article/arhiv.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            // проверка на пустой или нет
            System.out.println(al.isEmpty());

            for (int i = 0; i < al.size(); i++) {
                System.out.println("Добaвляю в архив: " + al.get(i));
                add(zos, al.get(i));
//                System.out.println("Удаляю файл: " + al.get(i));
//                File f = new File(al.get(i));
//                f.delete();
            }
            zos.close();
            fos.close();
        } else if (OS.matches("Windows.*")) {
            FileOutputStream fos = new FileOutputStream("d:\\picture_article\\arhiv.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (int i = 0; i < al.size(); i++) {
                System.out.println("Добавляю в архив: " + al.get(i));
                add(zos, al.get(i));
//                File f = new File(al.get(i));
//                f.delete();
                zos.close();
                fos.close();
            }
        }

    }

    private static void add(ZipOutputStream zos, String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        zos.closeEntry();
        fis.close();
    }
}
