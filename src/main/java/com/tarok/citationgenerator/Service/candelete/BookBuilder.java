package com.tarok.citationgenerator.Service.candelete;

import com.tarok.citationgenerator.Repository.Book;
import com.tarok.citationgenerator.Repository.ValueObjects.*;
import com.tarok.citationgenerator.Repository.ValueObjects.creator.translator.Translator;
import com.tarok.citationgenerator.Repository.ValueObjects.creator.author.Authors;
import com.tarok.citationgenerator.Repository.ValueObjects.isbn.IsbnImpl;
import com.tarok.citationgenerator.Repository.ValueObjects.publishedyear.PublishedYearImpl;

public class BookBuilder {
    private Title title;
    private Authors authors;
    private PublishedYearImpl publishedYear;
    private Publisher publisher;
    private IsbnImpl isbn;
    private Translator translator;

    public BookBuilder title(String titleString) {
        this.title = Title.nameOf(titleString);
        return this;
    }

    public BookBuilder authors(String authorString) {
        authors.add(authorString);
        return this;
    }

    public BookBuilder publishedYear(String yearString) {
        this.publishedYear = new PublishedYearImpl(yearString);
        return this;
    }

    public BookBuilder publisher(String publisherString) {
        this.publisher = new Publisher(publisherString);
        return this;
    }

    public BookBuilder isbn(String isbnString) {
        this.isbn = new IsbnImpl(isbnString);
        return this;
    }

    public BookBuilder translator(String translatorString) {
        this.translator = Translator.nameOf(translatorString);
        return this;
    }

    public static Book build() {
        return new Book();
    }


}
