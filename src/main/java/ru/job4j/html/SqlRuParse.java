package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Class SqlRuParse. The class is a vacancy parser for the sql.ru site.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 25.05.2020
 */
public class SqlRuParse implements Parse {

    /**
     * Gets posts list.
     *
     * @param link the url
     * @return List<Post>
     */

    @Override
    public List<Post> list(String link) {
        List<Post> allPosts = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Document doc = null;
            try {
                doc = Jsoup.connect(link + i).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements row = doc.select(".postslisttopic");
            for (Element element : row) {
                if (!element.text().startsWith("Важно:") && !element.child(1).hasClass("closedTopic")) {
                    allPosts.add(detail(element.child(0).attr("href")));
                }
            }
        }
        return allPosts;
    }

    /**
     * Gets post detail.
     *
     * @param link the url
     * @return the post data
     */

    @Override
    public Post detail(String link) {
        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element row = doc.select(".msgTable").first();
        String name = row.select(".messageHeader").first().select("td:matchText").text();
        String text = row.select(".msgBody").last().text();
        Timestamp created = DateParse.getDate(row.select(".msgFooter").text());
        return new Post(name, text, link, created);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        SqlRuParse sqlRuParse = new SqlRuParse();
        List<Post> posts = sqlRuParse.list("https://www.sql.ru/forum/job-offers/");
        for (Post el : posts) {
            System.out.println(el.getName());
            System.out.println(el.getText());
            System.out.println(el.getUrl());
            System.out.println(el.getCreated());
        }
        System.out.println("Number of vacancies : " + posts.size());
    }
}
