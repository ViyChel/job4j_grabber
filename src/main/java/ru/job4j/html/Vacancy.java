package ru.job4j.html;

import java.util.Objects;

public class Vacancy {
    private String description;
    private String url;
    private String date;

    public Vacancy(String description, String url, String date) {
        this.description = description;
        this.url = url;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Vacancy{"
                + "description='" + description + '\''
                + ", url='" + url + '\''
                + ", date='" + date + '\''
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
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(description, vacancy.description)
                && Objects.equals(url, vacancy.url)
                && Objects.equals(date, vacancy.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, url, date);
    }
}
