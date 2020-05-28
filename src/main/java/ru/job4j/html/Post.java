package ru.job4j.html;

import java.sql.Timestamp;
import java.util.Objects;

public class Post {
    private String name;
    private String text;
    private String url;
    private Timestamp created;

    public Post(String name, String text, String url, Timestamp created) {
        this.name = name;
        this.text = text;
        this.url = url;
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public Timestamp getCreated() {
        return created;
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
