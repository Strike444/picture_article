package ru.andreyshimanovich.picture_article;

/**
 * Created by Andrey Shimanovich on 05.06.2016.
 */
public class GetText {
    private String[] pdoc;
    public GetText(String[] s) {
        pdoc = s;
        paths(pdoc);
    }

    private void paths(String[] s) {
        for (String pathDoc:s
             ) {
            System.out.println(pathDoc);

        }
    }

}