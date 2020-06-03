package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;

import static ru.job4j.html.Date.getDate;

/**
 * Class SqlRuParse. The class is a vacancy parser for the sql.ru site.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 25.05.2020
 */
public class SqlRuParse {

    /**
     * Gets post data.
     *
     * @param url the url
     * @return the post data
     */
    public Post getPostData(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element row = doc.select(".msgTable").first();
        String name = row.select(".messageHeader").first().select("td:matchText").text();
        String text = row.select(".msgBody").last().text();
        Timestamp created = getDate(row.select(".msgFooter").text());
        return new Post(name, text, url, created);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
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
