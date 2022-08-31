package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.Isbn;
import com.tarok.quotegenerator.Repository.ValueObjects.Authors;
import com.tarok.quotegenerator.Repository.ValueObjects.PublishedYear;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Book {
    private String title;

    private Authors authors;

    private PublishedYear publishedYear;

    private String publisher;

    //情報の取得法によってはnullになってしまう可能性がある。entityのIDにはできないかな。
    private Isbn isbn;
    //nullでありうる
    private String translator;

    public String getAuthorNames() {
        return authors.getOrRepresentAuthorsName();
    }

    public void setAuthors(String author) {
        this.authors = new Authors(author);
    }

}
