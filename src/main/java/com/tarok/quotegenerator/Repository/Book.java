package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Book {
    private Title title;

    private Authors authors;

    private PublishedYear publishedYear;

    private Publisher publisher;

    //情報の取得法によってはnullになってしまう可能性がある。entityのIDにはできないかな。
    private Isbn isbn;
    //nullでありうる
    private Translator translator;

    public String getAuthorNames() {
        return authors.getOrRepresentAuthorsName();
    }

    public void setAuthors(String author) {
        this.authors = new Authors(author);
    }

}
