package ru.andreyshimanovich.picture_article;

import org.apache.poi.hwpf.HWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Andrey Shimanovich on 05.06.2016.
 */
public class GetText {
    private String[] pdoc;
    public GetText(String[] pdoc) throws IOException {
        this.pdoc = pdoc;
//        sizePdoc();
//        paths(this.pdoc);
    }

    public void sizePdoc() {
        if (this.pdoc.length > 1) {
            System.out.println("Внимание! В каталоге находится более одного файла .doc");
            System.out.println(this.pdoc.length + " файла .doc");
        }
        else if (this.pdoc.length == 1) {
            System.out.println("В каталоге находится один файл .doc");
        }
        else if (this.pdoc.length < 1) {
            System.out.println("Католог не содержит файлов .doc");
        }
    }

    public ArrayList<String> paths(String[] s) throws IOException {
        ArrayList<String> listDocTxt = new ArrayList<String>();
        for (String pathDoc:s
             ) {
            File docFile = new File(pathDoc);
            System.out.println("Обрабатываю файл: " + docFile.getName());
            FileInputStream inpStrm = new FileInputStream(docFile);
            HWPFDocument wordDoc = new HWPFDocument(inpStrm);
            String st = wordDoc.getText().toString();
            listDocTxt.add(st);
        }
        return listDocTxt;
    }
}
