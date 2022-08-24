package com.tarok.quotegenerator.Service;

import com.tarok.quotegenerator.Repository.Book;
import org.springframework.stereotype.Component;

@Component
public class BookSetter {
    private final Book book;

    public BookSetter(Book book) {
        this.book = book;
    }

//    public Book setBook() {
//        this.book.setAuthors();
//        this.book.setIsbn();
//        this.book.setPublishedYear();
//        this.book.setTitle();
//        this.book.setPublisher();
//        this.book.setTranslator();
//
//        return book;
//    }


}
