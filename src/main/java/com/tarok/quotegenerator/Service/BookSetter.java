package com.tarok.quotegenerator.Service;

import com.tarok.quotegenerator.Repository.Author;
import com.tarok.quotegenerator.Repository.Authors;
import com.tarok.quotegenerator.Repository.Book;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

@Component
public class BookSetter {
    private final Book book;
    private final GetBookService service;

    public BookSetter() {
        this.book = new Book();
        this.service = new GetBookService();
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
    public Book setAuthors(String isbn) throws XPathExpressionException, IOException {
        NodeList nodes = service.getNodesByISBN(isbn);
        for (int i = 0; i < nodes.getLength(); i++) {
//            System.out.println("number" + i + "は" + nodes.item(i).getNodeValue());
            this.book.setAuthors(new Authors((nodes.item(0).getNodeValue())));
        }

        return this.book;
    }


}
