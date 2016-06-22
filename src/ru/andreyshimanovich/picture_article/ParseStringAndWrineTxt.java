package ru.andreyshimanovich.picture_article;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Andrey Shimanovich on 20.06.16.
 */
public class ParseStringAndWrineTxt {
    private Map<String, String> arTxt;

    public ParseStringAndWrineTxt(Map<String, String> arTxt) throws FileNotFoundException {
        this.arTxt = arTxt;
        parse(arTxt);
    }

    private void parse(Map<String, String> arTxt) throws FileNotFoundException {

        // Получаем набор элементов
        Set<Map.Entry<String, String>> set = arTxt.entrySet();
        for (Map.Entry<String, String> me : set) {
            String text = me.getValue();

            String[] textmas = text.split("\r");

            // Длинна массива
//           System.out.println("S" + textmas.length);
//
//            //            System.out.print("# " + me.getKey() + ": ");
////            System.out.println("#" + me.getValue());
//
//
            for (int i = 0; i < textmas.length; i++) {
                while (textmas[i].matches("\\s.*")) {
                    System.out.println("Устраняю лишние пробелы");
                    textmas[i] = textmas[i].replaceFirst("\\s.*", "");
                }
            }


            for (int i = 0; i < textmas.length; i++) {
                while (textmas[i].matches("\\s.*")) {
                    System.out.println("Устраняю лишние пробелы");
                    textmas[i] = textmas[i].replaceFirst("\\s.*", "");
                }
            }


            for (int i = 0; i < textmas.length; i++) {
                textmas[i] = textmas[i].replaceAll("[\\u00A0\\s]+", " ");
            }

            for (int i = 0; i < textmas.length; i++) {
                textmas[i] = textmas[i].trim();
            }

            for (int i = 0; i < textmas.length; i++) {
                if (textmas[i].matches(".*\u0013 HYPERLINK.*\u0001\u0014.*\u0015.*")) {
                    System.out.println("!!! Нашел линк");
                    int pos = textmas[i].indexOf('\u0013');
                    int posend = textmas[i].indexOf('\u0001');
                    String substring = textmas[i].substring(pos + 13, posend - 1);
                    System.out.println(substring);
                    int posNaz = textmas[i].indexOf('\u0014');
                    int posNazEnd = textmas[i].indexOf('\u0015');
                    String substringNaz = textmas[i].substring(posNaz + 1, posNazEnd);
                    System.out.println(substringNaz);
                    String substringNach = textmas[i].substring(0, pos);
                    String substringConec = textmas[i].substring(posNazEnd + 1, textmas[i].length());
                    System.out.println(substringNach + "#" + substringConec);
                    textmas[i] = substringNach + "<a href=" + substring + "\">" + substringNaz + "</a>" + substringConec;
                    System.out.println(textmas[i]);
                }

            }


//                for (String s : textmas
//                        ) {
//                    while (s.matches("\\s.*")) {
//                        System.out.println("Устраняю лишние пробелы");
//                        s = s.replaceFirst("\\s.*", "");
//                    }
//                    // Не помню что делает
//
//                    s = s.replaceAll("[\\u00A0\\s]+", " ");
//                    s = s.trim();
//                    String b = "";
//                    if (s.matches(".*\u0013 HYPERLINK.*\u0001\u0014.*\u0015.*")) {
//                        System.out.println("!!! Нашел линк");
//                        int pos = s.indexOf('\u0013');
//                        int posend = s.indexOf('\u0001');
//                        String substring = s.substring(pos + 13, posend - 1);
//                        System.out.println(substring);
//                        int posNaz = s.indexOf('\u0014');
//                        int posNazEnd = s.indexOf('\u0015');
//                        String substringNaz = s.substring(posNaz + 1, posNazEnd);
//                        System.out.println(substringNaz);
//                        String substringNach = s.substring(0, pos);
//                        String substringConec = s.substring(posNazEnd + 1, s.length());
////                System.out.println(substringNach + "#" + substringConec);
//                        b = substringNach + "<a href=" + substring + "\">" + substringNaz + "</a>" + substringConec;
////              System.out.println(text);
//                        ;
//                    }
//                    s = b;
//                }
//
//            for (String s : textmas
//                    ) {
//                System.out.println("$$$" + s);
//            }


//
//
            System.out.println("Длинна массива обзатцев: " + textmas.length);
            ArrayList<String> list = new ArrayList<>();

            for (int k = 0; k < textmas.length; k++) {
                list.add(k, textmas[k]);
            }

            for (int f = 0; f < list.size(); f++) {
                if (list.get(f).equals("")) {
                    System.out.println("выловил пустое значение");
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
                    System.out.println("выловил еще значение с нулевой длинной");
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
                adres = null;
            } else {
                System.out.println("Фильм находится по адресу: " + adres);
            }

            System.out.println("Введен адрес: " + adres);

//            // TODO ага вот тут касяк с массивом
//            // Заполняем нужными тегами
            list.set(0, "<p style=\"line-height: normal; text-align: justify;\">" + list.get(0) + "</p>\n<hr id=\"system-readmore\" />");
            list.set(1, "<p style=\"line-height: normal; text-align: justify;\">" + list.get(1) + "<br /><br />");

            String c = "";

            String[] myArray = {}; // конвертируем ArrayList в массив
            myArray = list.toArray(new String[list.size()]);

            for (int fff = 0; fff < 1; fff++) {
                c = c + myArray[fff] + "\r\n";
            }
            for (int fff = 1; fff < myArray.length; fff++) {
                c = c + myArray[fff];
            }

            text = c;

//            me.setValue(c);
//
//
//// TODO перевести в аррей лист в текст и записать в text

            //Задаем значение
            me.setValue(text);
            // Запись в файл
            PrintWriter pw = new PrintWriter(new FileOutputStream(me.getKey().replaceAll(".doc", "") + ".txt"));
            pw.println(me.getValue());
            pw.close();
        }
    }
}

