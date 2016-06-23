package ru.andreyshimanovich.picture_article;

import javafx.scene.input.DataFormat;

import javax.imageio.ImageIO;
import javax.sound.midi.Soundbank;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.AbstractList;
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
    public static ArrayList<String> propJpg;
    //TODO сделать проверку на ширину и если ширина меньше чем надо то не конвертировать


//    public ArrayList<String> getPropJpg() {
//        return propJpg;
//    }

    public ResizeJpg(String[] masJpg) throws IOException {

        this.masJpg = masJpg;
        ArrayList<String> filesForArh = toArh(this.masJpg);
        toZip(filesForArh);
        renameFiles(this.masJpg);
        // ХЗ
        String[] masRenamedJpg = getJpgAfterRename();
        getFiles(masRenamedJpg);

//        ArrayList<String> filesForDel = resize(this.masJpg);
        ArrayList<String> filesForDel = resize(masRenamedJpg);
        delAfterZip(filesForDel);
        renameGlav(masRenamedJpg);
        propJpg = afterAllconverts();
    }


    private void renameGlav(String[] g) {
        for( int i = 0; i < g.length; i++) {
            File file = new File(g[i]);
            System.out.println(g[i].getClass() + g[i]);
//            g[i]
            if(g[i].matches(".*glav_ish.jpg")) {
                System.out.println("Выловил главный");
                if(file.exists()) {
                    System.out.println("Переименовываю назад главный файл");
                    File file1 = new File(file.getPath().toString().replaceAll("glav_ish.jpg", "glav.jpg"));
                    file.renameTo(file1);
                }
            }
        }
    }

    // после всех преобразований с картинками выводим аррей лист с путем и размерами миниатюрч
    private ArrayList<String> afterAllconverts() throws IOException {

        ArrayList<String> arrayList = new ArrayList<String>();
        DirScanner drr = new DirScanner();
        String dirr = drr.PathDirScanner();
        String[] jpgAfterRename = drr.getMasJpg(dirr);
        for (int i = 0; i < jpgAfterRename.length; i++) {
            File file = new File(jpgAfterRename[i]);
            BufferedImage im = ImageIO.read(file);
            int scaleW = im.getWidth();
            System.out.println("Ширина картинки: " + scaleW);
            int scaleH = im.getHeight();
            System.out.println("Высота картинки: " + scaleH);
            int newHmin = 150;
            int newWmin = (150 * scaleW) / scaleH;
            arrayList.add(file.getPath() + "," + newHmin + "," + newWmin );
        }

        return arrayList;

    }

    // Получить массив jpg после переименования
    private String[] getJpgAfterRename () {
        DirScanner drr = new DirScanner();
        String dirr = drr.PathDirScanner();
        String[] jpgAfterRename = drr.getMasJpg(dirr);
        return jpgAfterRename;
    }

    private void renameFiles(String [] masJpg) {
        for (int i = 0; i < masJpg.length; i++) {

            System.out.println("Файл до переименования: " + masJpg[i]);
            File file = new File(masJpg[i]);
            File file1 = new File(file.getPath().toString().replaceAll(".jpg", "_ish.jpg"));
            file.renameTo(file1);
        }
    }

    private void getFiles(String[] masJpg) {
        for (String s: masJpg
             ) {
            System.out.println("Файл после переименования: " + s);
        }
    }


    // Выбираю файлы для архивации
    private ArrayList<String> toArh(String[] masJpg) {
        ArrayList<String> masForArh = new ArrayList<String>();
        for (String s : masJpg) {
            File file = new File(s);
            System.out.println("Помечаю файл для архива: " + file);
            masForArh.add(file.toString());
        }
        return masForArh;
    }

    private ArrayList<String> resize(String[] masJpg) throws IOException {
        int i = 0;
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



//            String ishFile = new String(file.getPath().toString().replaceAll(".jpg", "_ish.jpg"));
//            File file1 = new File(file.getPath().toString().replaceAll(".jpg", "_ish.jpg"));
//            file.renameTo(file1);

            if (OS.equals("Linux")) {
                if (file.getPath().toString().equals(file.getParent() + "/" + i + ".jpg")) {
                    System.out.println("* Найдено сопадение имен исходного " + file.getName() + " и выходного файлов " +
                            i + ".jpg");
                    System.out.println("По идее это вообще никогда не напечатается");
                    //TODO сделать тут проверку на _ish_ish по регулярному выражению
//                    System.out.println("Выполняю переименование исходного файла " + file.getName());
//                    String ishFile = new String(file.getPath().toString().replaceAll(".jpg", "_ish.jpg"));
//                    System.out.println("Исходный файл после переименования: " + ishFile);
//                    ImageIO.write(scaled, "JPEG", new File(file.getPath()));
//                    i--;
                    // TODO не конвентирует исходный файл
                } else {
                    // Проверка на главыный файл
                    if (file.getPath().toString().equals(file.getParent() + "/" + "glav_ish.jpg") ) {
                        System.out.println("Обнаружен главный файл: " + file.getPath());
                        ImageIO.write(scaled, "JPEG", new File(file.getPath()));
                        i--;
                    } else {
                        System.out.println("Добовляю файл в список на удаление " + file);
                        masOldJpg.add(file.toString());
                        ImageIO.write(scaled, "JPEG", new File(file.getParent() + "/" + i + ".jpg"));
                    }
                }
            } else {
                if (OS.matches("Windows.*")) {
                    ImageIO.write(scaled, "JPEG", new File(file.getParent() + "\\" + i + ".jpg"));
                }
            }
            i++;
        }
        System.out.println("Длинна " + masOldJpg.size());
        return masOldJpg;
    }


    // Удаление файлов после архивации
    private void delAfterZip(ArrayList<String> masForDel) {
        for (String s : masForDel
                ) {
            File f = new File(s);
            if (f.exists()) {
                f.delete();
            }
        }
    }

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
            }
            zos.close();
            fos.close();
        } else if (OS.matches("Windows.*")) {
            FileOutputStream fos = new FileOutputStream("d:\\picture_article\\arhiv.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (int i = 0; i < al.size(); i++) {
                System.out.println("Добавляю в архив: " + al.get(i));
                add(zos, al.get(i));
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
