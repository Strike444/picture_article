package ru.andreyshimanovich.picture_article;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andrey Shimanovich on 07.07.16.
 */
public class MoveToFolder {
    private final String[] masJpg;
    private final String date;
    private final String dir;

    public MoveToFolder(String[] masJpg, String dir) {
        this.masJpg = masJpg;
        date = getDate();
        this.dir = dir;
        createFolder();
        moveToF(this.masJpg);
    }

    private void createFolder() {
        Path p = FileSystems.getDefault().getPath(dir);

        String pathsd = dir + "/" + date;
        Path ptd = FileSystems.getDefault().getPath(pathsd);
        try {
            Files.createDirectories(ptd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDate() {
        Date date = new Date();
//        System.out.println(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
//        System.out.println(simpleDateFormat.format(date));
        String fdate = simpleDateFormat.format((date));
        return fdate;
    }


    public void moveToF(String[] masJpg) {
        for (String s : masJpg
                ) {
            System.out.println("Файл для перемещения: " + s);
//            Files.move(s, s + "\123", StandardCopyOption.REPLACE_EXISTING)
            Path ptj = FileSystems.getDefault().getPath(s);
            File f = new File(s);
            //TODO тут возможно не будет работать в виндовсе
            String pathsd = dir + "/" + date + "/" + f.getName();
            Path ptjd = FileSystems.getDefault().getPath(pathsd);
//            System.err.println(ptjd);

            try {
                Files.move(ptj, ptjd, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
