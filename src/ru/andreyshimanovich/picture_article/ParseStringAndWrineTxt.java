package ru.andreyshimanovich.picture_article;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Andrey Shimanovich on 20.06.16.
 */
public class ParseStringAndWrineTxt {
    private Map<String,String> arTxt;

    public ParseStringAndWrineTxt(Map<String,String> arTxt) throws FileNotFoundException {
        this.arTxt = arTxt;
        parse(arTxt);
    }

    private void parse(Map<String,String> arTxt) throws FileNotFoundException {

        // Получаем набор элементов
        Set<Map.Entry<String, String>> set = arTxt.entrySet();
        for (Map.Entry<String, String> me : set) {
            String text = me.getValue();
            //            System.out.print("# " + me.getKey() + ": ");
//            System.out.println("#" + me.getValue());


            while (text.matches("\\s.*")) {
                System.out.println("Устраняю лишние пробелы");
                text = text.replaceFirst("\\s.*", "");
            }

            text = text.replaceAll("[\\u00A0\\s]+", " ");
            text = text.trim();

            if (text.matches(".*\u0013 HYPERLINK.*\u0001\u0014.*\u0015.*")) {
                System.out.println("!!! Нашел линк");
                int pos = text.indexOf('\u0013');
                int posend = text.indexOf('\u0001');
                String substring = text.substring(pos + 13, posend - 1);
                System.out.println(substring);
                int posNaz = text.indexOf('\u0014');
                int posNazEnd = text.indexOf('\u0015');
                String substringNaz = text.substring(posNaz + 1, posNazEnd);
                System.out.println(substringNaz);
                String substringNach = text.substring(0, pos);
                String substringConec = text.substring(posNazEnd + 1, text.length());
//                System.out.println(substringNach + "#" + substringConec);
                text = substringNach + "<a href=" + substring + "\">" + substringNaz + "</a>" + substringConec;
//              System.out.println(text);
            }

            String[] masText = text.split("\r");
            System.out.println("$$$" + masText.length);
            ArrayList<String> list = new ArrayList<>();

            for (int k = 0; k < masText.length; k++) {
                list.add(k, masText[k]);
            }

            for (int f = 0; f < list.size(); f++) {
                if (list.get(f).equals("")) {
//                System.out.println("выловил пустое значение");
                    list.remove(f);
                }
            }
            for (int ffff = 0; ffff < list.size(); ffff++) {
                if (list.get(ffff).matches("[^\\wА-яЁё]+")) {
                    System.out.println("Ага");
                    System.out.println("Ага: " + list.get(ffff));
                    list.remove(ffff);
                }
            }
            for (int ffa = 0; ffa < list.size(); ffa++) {
                if (list.get(ffa).length() == 0) {
//                System.out.println("выловил еще значение с нулевой длинной");
                    list.remove(ffa);
                }
            }

            // Запрос на адрес фидеофайла
            String adres = "";
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите адрес видеофайла (если его нет просто нажимите enter)");

//            if(sc.hasNext()) {
                adres = sc.nextLine();
            if (adres.length() == 0) {
                System.out.println("Фильма нет");
            }
            else {
                System.out.println("Фильм находится по адресу: " + adres);
            }
//            }
//            else  {
//                adres = null;
//            }

            System.out.println("Введен адрес: " + adres);

            // TODO ага вот тут касяк с массивом
            // Заполняем нужными тегами
//            list.set(0, "<p style=\"line-height: normal; text-align: justify;\">" + list.get(0) + "</p>\n<hr id=\"system-readmore\" />");
//            list.set(1, "<p style=\"line-height: normal; text-align: justify;\">" + list.get(1) + "<br /><br />");

// TODO перевести в аррей лист в текст и записать в text

            //Задаем значение
            me.setValue(text);
            // Запись в файл
            PrintWriter pw = new PrintWriter(new FileOutputStream(me.getKey().replaceAll(".doc","") + ".txt"));
            pw.println(me.getValue());
            pw.close();
        }
    }
}

