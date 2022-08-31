package com.tarok.quotegenerator.Repository.ValueObjects;

import com.tarok.quotegenerator.Repository.ValueObjects.Author;
import lombok.Data;
import java.util.List;

@Data
public class Authors {
    private List<Author> authors;

    public Authors(List<Author> authors) {
        this.authors = authors;
    }
    public Authors(Author author) {
        this.authors = List.of(author);
    }

    public Authors(String author) {
        this.authors = List.of(new Author(author));
    }

    //作者が三人以上であるとき最初の作者に「...他」をつけて代表させる。
    public String getOrRepresentAuthorsName() {
        if(authors.size() > 2) {
            return authors.get(0).toString() + "他";
        }

        if(authors.size() > 1) {
            return authors.get(0).toString() + "・" + authors.get(1).toString();
        }

        return authors.get(0).toString();
    }

}
