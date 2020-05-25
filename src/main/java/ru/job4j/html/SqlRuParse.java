package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
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
