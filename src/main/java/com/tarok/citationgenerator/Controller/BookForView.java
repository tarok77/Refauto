package com.tarok.citationgenerator.Controller;

import com.tarok.citationgenerator.Repository.RawBook;
import lombok.Data;

/**
 * modelにのせviewに送るためのクラス
 */
@Data
public class BookForView {
    private String title;

    private String creators;

    private String publishedYear;

    private String publisher;

    private String isbn;

    private BookForView(RawBook book) {
        this.title = book.getTitle();
        this.publishedYear = book.getPublishedYear();
        this.publisher = book.getPublisher();
        this.isbn = book.getIsbn();

        this.creators = book.getCreatorList().stream().reduce(" ",(a, b) -> a + " " + b).trim();

    }

    public static BookForView toView(RawBook book) {
        return new BookForView(book);
    }

}