package ru.job4j.html;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Class Post. This is the data model for the vacancy.
 *
 * @author Vitaly Yagufarov (for.viy@gmail.com)
 * @version 1.0
 * @since 28.05.2020
 */
public class Post {
    private String name;
    private String text;
    private String url;
    private Timestamp created;

    /**
     * Instantiates a new Post.
     *
     * @param name    the name
     * @param text    the text
     * @param url     the url
     * @param created the created
     */
    public Post(String name, String text, String url, Timestamp created) {
        this.name = name;
        this.text = text;
        this.url = url;
        this.created = created;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets created.
     *
     * @return the created
     */
    public Timestamp getCreated() {
        return created;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Post{"
                + "name='" + name + '\''
                + ", text='" + text + '\''
                + ", url='" + url + '\''
                + ", created=" + created
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(name, post.name)
                && Objects.equals(text, post.text)
                && Objects.equals(url, post.url)
                && Objects.equals(created, post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, text, url, created);
    }
}
