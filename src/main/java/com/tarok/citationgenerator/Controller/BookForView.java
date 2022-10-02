package com.tarok.citationgenerator.Controller;

import com.tarok.citationgenerator.Repository.RawBook;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * modelにのせviewにRawBookを送るためのクラス
 */
@Data
@NoArgsConstructor
public class BookForView {
    private String title;

    private String creators;

    private String publishedYear;

    private String publisher;

    private String isbn;

    public BookForView(RawBook book) {
        this.title = book.getTitle();
        this.publishedYear = book.getPublishedYearAndMonth();
        this.publisher = book.getPublisher();
        this.isbn = book.getIsbn();

        //TODO　もっとましな書き方ありそう
        this.creators = book.getCreatorList().stream().reduce(" ",(a, b) -> a + "," + b).replaceFirst(",","").trim();
    }

    public static BookForView ConvertForView(RawBook book) {
        return new BookForView(book);
    }

}
