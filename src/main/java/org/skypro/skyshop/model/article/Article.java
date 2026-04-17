package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public class Article implements Searchable {
    private final UUID id;
    private final String title;
    private final String text;

    public Article(String title, String text, UUID id) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Article article = (Article) obj;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return (title != null ? title : "") + "\n" + (text != null ? text : "");
    }

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return title;
    }
    @JsonIgnore
    @Override
    public String getContentType() {
        return "ARTICLE";
    }
    @JsonIgnore
    @Override
    public String getName() {
        return title;
    }

}
