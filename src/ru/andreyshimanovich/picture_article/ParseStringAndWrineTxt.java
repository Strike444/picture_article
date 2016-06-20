package ru.andreyshimanovich.picture_article;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.AbstractList;

/**
 * Created by Andrey Shimanovich on 20.06.16.
 */
public class ParseStringAndWrineTxt {
    private AbstractList<String> arTxt;

    public ParseStringAndWrineTxt(AbstractList<String> arTxt) throws FileNotFoundException {
        this.arTxt = arTxt;
        parse(arTxt);
    }

    private void parse(AbstractList<String> arTxt) throws FileNotFoundException {
        for (String s : arTxt
                ) {
            while (s.matches("\\s.*")) {
                System.out.println("Устраняю лишние пробелы");
                s.replaceFirst("\\s.*", "");
            }

            s = s.replaceAll("[\\u00A0\\s]+", " ");
            s = s.trim();

            if (s.matches(".*\u0013 HYPERLINK.*\u0001\u0014.*\u0015.*")) {
                System.out.println("!!! Нашел линк");
                int pos = s.indexOf('\u0013');
                int posend = s.indexOf('\u0001');
                String substring = s.substring(pos + 13, posend - 1);
                System.out.println(substring);
                int posNaz = s.indexOf('\u0014');
                int posNazEnd = s.indexOf('\u0015');
                String substringNaz = s.substring(posNaz + 1, posNazEnd);
                System.out.println(substringNaz);
                String substringNach = s.substring(0, pos);
                String substringConec = s.substring(posNazEnd + 1, s.length());
                System.out.println(substringNach + "#" + substringConec);
                s = substringNach + "<a href=" + substring + "\">" + substringNaz + "</a>" + substringConec;
                System.out.println(s);

            }


            // Для тестирования
            PrintWriter pw = new PrintWriter(new FileOutputStream("/home/strike/picture_article/out.txt"));
            pw.println(s);
            pw.close();
        }
    }
}

