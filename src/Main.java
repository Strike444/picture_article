package src;

import ru.andreyshimanovich.picture_article.DirScanner;
import ru.andreyshimanovich.picture_article.GetText;
import ru.andreyshimanovich.picture_article.ParseStringAndWrineTxt;
import ru.andreyshimanovich.picture_article.ResizeJpg;

import java.io.IOException;
import java.util.AbstractList;

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
        AbstractList<String> arTxt = gt.paths(masDoc);
        ParseStringAndWrineTxt psawt = new ParseStringAndWrineTxt(arTxt);
    }
}
