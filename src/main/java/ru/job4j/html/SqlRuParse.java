package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(SqlRuParse.class.getName());

    private Document doc;

    /**
     * Gets posts list.
     *
     * @param link the url
     * @return List<Post>
     */

    @Override
    public List<Post> list(String link) {
        List<Post> result = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            try {
                this.doc = Jsoup.connect(link + i).get();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
            Elements row = doc.select(".postslisttopic");
            for (Element element : row) {
                if (!element.text().startsWith("Важно:") && !element.child(1).hasClass("closedTopic")) {
                    result.add(detail(element.child(0).attr("href")));
                }
            }
        }
        return result;
    }

    /**
     * Gets post detail.
     *
     * @param link the url
     * @return the post data
     */

    @Override
    public Post detail(String link) {
        try {
            this.doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
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
        for (Post el : sqlRuParse.list("https://www.sql.ru/forum/job-offers/")) {
            System.out.println(el.getName());
            System.out.println(el.getText());
            System.out.println(el.getUrl());
            System.out.println(el.getCreated());
        }
    }
}
