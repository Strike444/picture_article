package src;

import ru.andreyshimanovich.picture_article.DirScanner;
import ru.andreyshimanovich.picture_article.GetText;
import ru.andreyshimanovich.picture_article.ResizeJpg;

import java.io.IOException;

/**
 * Created by Andrey Shimanovich on 05.06.2016.
 */
public class Main {
    public static void main(String args[]) throws IOException {
        DirScanner dr = new DirScanner();
        String dir = dr.PathDirScanner();
        String[] masDoc = dr.getMasDoc(dir);
        String[] masJpg = dr.getMasJpg(dir);
        GetText gt = new GetText(masDoc);
        ResizeJpg rj = new ResizeJpg(masJpg);
    }
}
