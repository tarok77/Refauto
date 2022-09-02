package com.tarok.quotegenerator.Service;

import com.tarok.quotegenerator.Repository.Book;
import com.tarok.quotegenerator.Repository.ValueObjects.*;

public class BookBuilder {
    private Title title;
    private Authors authors;
    private PublishedYear publishedYear;
    private Publisher publisher;
    private Isbn isbn;
    private Translator translator;

    public BookBuilder title(String titleString) {
        this.title = new Title(titleString);
        return this;
    }

    public BookBuilder authors(String authorString) {
        authors.add(authorString);
        return this;
    }

    public BookBuilder publishedYear(String yearString) {
        this.publishedYear = new PublishedYear(yearString);
        return this;
    }

    public BookBuilder publisher(String publisherString) {
        this.publisher = new Publisher(publisherString);
        return this;
    }

    public BookBuilder isbn(String isbnString) {
        this.isbn = new Isbn(isbnString);
        return this;
    }

    public BookBuilder translator(String translatorString) {
        this.translator = new Translator(translatorString);
        return this;
    }

    public static Book build() {
        return new Book();
    }


}
