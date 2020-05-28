package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SqlRuParse {
    private final static String[] MONTHS = {"янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};

    public static Timestamp getDate(String date) {
        String[] arr = date.split(" ");
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        if (arr[0].equals("сегодня,")) {
            sb.append(sdf.format(calendar.getTime())).append(" ")
                    .append(arr[1]).append(":00");

        } else if (arr[0].equals("вчера,")) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            sb.append(sdf.format(calendar.getTime())).append(" ")
                    .append(arr[1]).append(":00");

        } else {
            sb.append("20").append(arr[2], 0, 2).append("-")
                    .append(convertMonth(arr[1])).append("-")
                    .append(arr[0]).append(" ")
                    .append(arr[3]).append(":00");
        }
        return Timestamp.valueOf(sb.toString());
    }

    private static String convertMonth(String month) {
        int result = 0;
        for (int i = 0; i < MONTHS.length; i++) {
            if (MONTHS[i].equals(month)) {
                result = i + 1;
                break;
            }
        }
        return Integer.toString(result);
    }

    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 6; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element element : row) {
                if (!element.text().startsWith("Важно:") && !element.child(1).hasClass("closedTopic")) {
                    Element date = element.parent().select(".altCol").last();
                    System.out.println(element.child(0).attr("href"));
                    System.out.println(element.child(0).text());
                    System.out.println(date.text());
                }
            }
        }
    }
}
