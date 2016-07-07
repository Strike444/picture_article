package ru.andreyshimanovich.picture_article;

import ru.andreyshimanovich.picture_article.DirScanner;
import ru.andreyshimanovich.picture_article.GetText;
import ru.andreyshimanovich.picture_article.ParseStringAndWrineTxt;
import ru.andreyshimanovich.picture_article.ResizeJpg;

import java.io.IOException;
import java.util.AbstractList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrey Shimanovich on 05.06.2016.
 */
public class Main {
    public static void main(String args[]) throws IOException {
        DirScanner dr = new DirScanner();
        String dir = dr.PathDirScanner();
        dr.lowNames(dir);
        String[] masDoc = dr.getMasDoc(dir);
        String[] masJpg = dr.getMasJpg(dir);
        new ResizeJpg(masJpg);
        GetText gt = new GetText(masDoc);
        gt.sizePdoc();
        Map<String,String> arTxt = gt.paths(masDoc);
        ParseStringAndWrineTxt psawt = new ParseStringAndWrineTxt(arTxt);
        DirScanner drj = new DirScanner();
        String dirj = dr.PathDirScanner();
        dr.lowNames(dirj);
        MoveToFolder mtf = new MoveToFolder(drj.getMasJpg(dirj), dirj);
//        new MoveToFolder(drj.getMasJpg(dirj, dirj));

    }
}
