package ru.andreyshimanovich.picture_article;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Andrey Shimanovich on 05.06.2016.
 */
public class DirScanner {
    private final String HOME = System.getProperty("user.home");
    private final String OS = System.getProperty("os.name");
    private String path;
    private File f;

    public void lowNames(String pds) {
        File f = new File(pds);
        File[] filesindir = f.listFiles();
        ArrayList<File> listfiles = new ArrayList<>();
        if (filesindir != null) {
            for (int i = 0; i < filesindir.length; i++) {
                listfiles.add(i, filesindir[i]);
            }
        }
        if (OS.equals("Linux")) {
            for (int i = 0; i < listfiles.size(); i++) {
                listfiles.get(i).renameTo(new File(listfiles.get(i).getParent() + "/" + listfiles.get(i).getName().toLowerCase()));
            }
        } else {
            if (OS.matches("Windows.*")) {
                for (int i = 0; i < listfiles.size(); i++) {
                    listfiles.get(i).renameTo(new File(listfiles.get(i).getParent() + "\\" + listfiles.get(i).getName().toLowerCase()));
                }
            }
        }
    }

    public String PathDirScanner() {

        if (OS.equals("Linux")) {
            System.out.println("It uses Linux");
            path = HOME + "/picture_article";
            f = new File(path);
            if (f.exists() && f.isDirectory()) {
            } else {
                f.mkdirs();
            }
        } else {
            if (OS.matches("Windows.*")) {
                System.out.println("It uses Windows");
                path = "d:\\" + "picture_article";
                System.out.println("Path: \"" + path + "\"");
                f = new File(path);
                if (f.exists() && f.isDirectory()) {
                } else {
                    f.mkdirs();
                }
            }
        }
        return path;
    }

    public String[] getMasDoc(String pds) {
        File f = new File(pds);
        File[] filesindir = f.listFiles();
        ArrayList<File> listfiles = new ArrayList<>();
        ArrayList<String> listdocfiles = new ArrayList<>();
        if (filesindir != null) {
            for (int i = 0; i < filesindir.length; i++) {
                listfiles.add(i, filesindir[i]);
            }
            for (int i = 0; i < listfiles.size(); i++) {
                if (listfiles.get(i).toString().matches(".*.doc")) {
                    System.out.println(".doc file: " + listfiles.get(i) + "\"");
                    listdocfiles.add(listfiles.get(i).toString());
                }
            }
            String[] masDoc = listdocfiles.toArray(new String[listdocfiles.size()]);
            return masDoc;
        } else {
            System.out.println("Нет .doc файлов в " + path + ".");
            return new String[0];
        }
    }

    public String[] getMasJpg(String pds) {
        f = new File(pds);
        File[] filesindir = f.listFiles();
        ArrayList<File> listfiles = new ArrayList<>();
        ArrayList<String> listdocfiles = new ArrayList<>();
        if (filesindir != null) {
            for (int i = 0; i < filesindir.length; i++) {
                listfiles.add(i, filesindir[i]);
            }
            for (int i = 0; i < listfiles.size(); i++) {
                if (listfiles.get(i).toString().matches(".*.jpg")) {
                    System.out.println(".jpg file: " + listfiles.get(i) + "\"");
                    listdocfiles.add(listfiles.get(i).toString());
                }
            }
            String[] masJpg = listdocfiles.toArray(new String[listdocfiles.size()]);
            return masJpg;
        } else {
            System.out.println("Нет .jpg файлов в " + path + ".");
            return new String[0];
        }
    }
}
