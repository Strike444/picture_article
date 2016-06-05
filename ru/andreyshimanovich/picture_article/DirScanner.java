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

    public String PathDirScanner() {

        if (OS.equals("Linux")) {
            System.out.println("It uses Linux");
            path = HOME + "/picture_article";
        } else {
            if (OS.matches("Windows.*")) {
                System.out.println("It uses Windows");
                path = "d:\\" + "picture_article";
                System.out.println("Path: \"" + path +"\"");
            }
        }
        return path;
    }

    public String[] getMasDoc(String pds) {
        File f = new File(pds);
        File[] filesindir = f.listFiles();
        ArrayList<File> listfiles = new ArrayList<>();
        ArrayList<String> listdocfiles = new ArrayList<>();
        for (int i = 0; i < filesindir.length; i++) {
            listfiles.add(i, filesindir[i]);
        }
        for (int i = 0; i < listfiles.size(); i++) {
            if (listfiles.get(i).toString().matches(".*.doc")) {
                System.out.println(".doc file: " + listfiles.get(i) +"\"");
                listdocfiles.add(listfiles.get(i).toString());
            }
        }
        String[] masDoc = listdocfiles.toArray(new String[listdocfiles.size()]);
        return masDoc;
    }

    public String[] getMasJpg(String pds) {
        f = new File(pds);
        File[] filesindir = f.listFiles();
        ArrayList<File> listfiles = new ArrayList<>();
        ArrayList<String> listdocfiles = new ArrayList<>();
        for (int i = 0; i < filesindir.length; i++) {
            listfiles.add(i, filesindir[i]);
        }
        for (int i = 0; i < listfiles.size(); i++) {
            if (listfiles.get(i).toString().matches(".*.jpg")) {
                System.out.println(".jpg file: " + listfiles.get(i) +"\"");
                listdocfiles.add(listfiles.get(i).toString());
            }
        }
        String[] masJpg = listdocfiles.toArray(new String[listdocfiles.size()]);
        return masJpg;
    }
}


