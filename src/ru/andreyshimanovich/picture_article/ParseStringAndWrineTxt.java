package ru.andreyshimanovich.picture_article;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import static ru.andreyshimanovich.picture_article.ResizeJpg.propJpg;

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

//        int kk = 0;

        // Получаем набор элементов
        Set<Map.Entry<String, String>> set = arTxt.entrySet();
        for (Map.Entry<String, String> me : set) {
            String text = me.getValue();

            String[] textmas = text.split("\r");

            for (int i = 0; i < textmas.length; i++) {
                System.out.println("Устраняю лишние пробелы");
//                    textmas[i] = textmas[i].trim();
                textmas[i] = textmas[i].replaceAll("\\s\\s+", " ");
            }

            for (int i = 0; i < textmas.length; i++) {
                textmas[i] = textmas[i].replaceAll("[\\u00A0\\s]+", " ");
            }

            for (int i = 0; i < textmas.length; i++) {
                textmas[i] = textmas[i].trim();
            }

            // для проверки
//            for(int i = 0; i<textmas.length; i++) {
//                text = text + textmas[i] + "\r\n";
//            }
//            PrintWriter pww = new PrintWriter(new FileOutputStream(me.getKey().replaceAll(".doc", "") + "пример.txt"));
//            pww.println(text);
//            pww.close();

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

            // Получим значения миниатюр
            if (propJpg.size() != 0) {
                for (String s : propJpg
                        ) {
                    // Пока вывод аррей листа
                    System.out.println(s);
                }
            }

            // Получаем дату
            Date date = new Date();
            System.out.println(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
            System.out.println(simpleDateFormat.format(date));
            String fdate = simpleDateFormat.format((date));

            // Получаем значения миниатюр главного файла
            String glavmin = "ы";

            for (String s : propJpg
                    ) {
                if (s.matches(".*glav.jpg.*")) {
                    System.out.println(s);
                    glavmin = s.replaceAll(".*glav.jpg,", "");
                    System.out.println(glavmin);
                    String[] glavminar = glavmin.split(",");
                    glavmin = "width=\"" + glavminar[1] + " height=\"" + glavminar[0] + "\"";
                    System.out.println(glavmin);
                }
            }

            // Получаем массив миниатюр без главного файла

//            Map<Integer, String> minj = new HashMap<Integer, String>();
            ArrayList<String> pathPlasProp = new ArrayList<String>();

//            ArrayList<String> minj = new ArrayList<String>();
            for (int i = 0; i < propJpg.size(); i++) {
//                String str = Integer.toString(i);
                String strWithoutPath = propJpg.get(i).substring(propJpg.get(i).length() - 14, propJpg.get(i).length()); //(".*\d+.jpg.*", "");
                String nomer = null;
                int nomerInt = -1;
//                System.err.println(strWithoutPath);
                if (strWithoutPath.matches("\\D\\d.*")) {
                    strWithoutPath = strWithoutPath.substring(1, strWithoutPath.length());
//                    System.err.println(strWithoutPath);
                }
//                System.err.println(strWithoutPath);

                if (strWithoutPath.matches("\\d.*")) {
                    nomer = strWithoutPath.substring(0, strWithoutPath.length() - 12);
//                    System.err.println("Номер " + nomer);
                    pathPlasProp.add(nomer + strWithoutPath.substring(strWithoutPath.length() - 8, strWithoutPath.length()));
                }
            }

            for (int i = 0; i < pathPlasProp.size(); i++) {
                if (pathPlasProp.get(i).matches("^\\D+.*")) {
                    pathPlasProp.remove(i);
                }
//                System.err.println(pathPlasProp.get(i));
            }

//            for (int i = 0; i < pathPlasProp.size(); i++) {
//                System.err.println(pathPlasProp.get(i));
//            }

//            ArrayList<String> paramJpg = new ArrayList<String>();
            String[] mapamJpgmas = new String[pathPlasProp.size()];
            for (int i = 0; i < pathPlasProp.size(); i++) {
                String[] mas = pathPlasProp.get(i).split(",");
//                int index = pathPlasProp.indexOf(pathPlasProp.get(i));
//                System.out.println(index);
                int mas0 = Integer.parseInt(mas[0]);
//                System.out.println("!!!!!!!!!" + mas0);

                mapamJpgmas[mas0] = mas[0] + ".jpg\" width=\"" + mas[2] + "\" height=\"" + mas[1] + "\"";

//                for (int j = 0; j < paramJpg.size(); j++) {
//                    if (mas0 == i) {
//                        System.err.println("есть контакт");
//                        paramJpg.add(mas[0] + ".jpg\" width=\"" + mas[2] + "\" height=\"" + mas[1] + "\"");
//                    }
//                }
            }

            for (String s : mapamJpgmas
                    ) {
                System.out.println(s + " Размеры для файлов");
            }


//            System.err.println("Длинна list " + list.size());
//            // Заполняем нужными тегами
            list.set(0, "<p style=\"line-height: normal; text-align: justify;\"><img src=\"images/stories/Glav/"
                    + fdate + "/glav.jpg\" " + glavmin + " style=\"margin: 5px; float: left;\" />"
                    + list.get(0) + "</p>\n<hr id=\"system-readmore\" />");

//            if (list.size() == 1) {
//
//            }

            if (list.size() > 1) {
                list.set(1, "<p style=\"line-height: normal; text-align: justify;\">" + list.get(1) + "<br /><br />");
                for (int i = 2; i < list.size(); i++) {
                    list.set(i, list.get(i) + "<br /><br />");
                }
            }

            if (list.size() > 1) {
                list.add("</p>\r\n");
            }

            list.add("<p>&nbsp;</p>\n\r");

            if (adres != null) {
                list.add("<p style=\"line-height: normal; text-align: center;\"><a href=\"" +
                        adres + "\"><span style=\"font-size: 14pt;\"><strong>Видеофильм</strong></span></a>\r\n</p>\r\n");
            }

            list.add("<table border=\"0\" align=\"center\">\r\n");
            list.add("\t<tbody>\r\n");
//            list.add("\t\t<tbody>\r\n");


//            System.err.println("Размер массива" + mapamJpgmas.length);

            double x = mapamJpgmas.length / 4.d;

            x = Math.ceil(x);

            int ix = (int) x;
//            System.err.println("Количество строк картинок: " + ix);

//            String[][] matrixA;
//            matrixA = new String[ix][4];


            int kk = 0;


            for (int i = 0; i < ix; i++) {
//                System.err.println(mapamJpgmas.length);

                list.add("\t\t<tr>\r\n");
                for (int j = 0; j < 4; j++) {
                    if (kk < mapamJpgmas.length) {
//                            System.err.println("K равно: " + kk);
                        list.add("\t\t\t<td><img src=\"images/stories/Glav/" + fdate + "/" + mapamJpgmas[kk] + "/>\r\n");
                        kk++;
                    }
                }
                list.add("\t\t</tr>\r\n");
//                    System.out.println("э");
            }
            list.add("\t</tbody>\r\n");
            list.add("</table>");

            kk = 0;

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
            //Задаем значение
            me.setValue(text);
            // Запись в файл
            PrintWriter pw = new PrintWriter(new FileOutputStream(me.getKey().replaceAll(".doc", "") + ".txt"));
            pw.println(me.getValue());
            pw.close();
        }
    }
}

