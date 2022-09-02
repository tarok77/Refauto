package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookTest {
    Book book = new Book();

    @BeforeEach
    public void setUpBook() {
        this.book.setAuthors("王陽明");
        this.book.setTitle(new Title("伝習録"));
        this.book.setIsbn(new Isbn("9784121600820"));
        this.book.setPublisher(new Publisher("中央公論新社"));
        this.book.setPublishedYear(new PublishedYear("2005.9"));
        this.book.setTranslator(new Translator("溝口雄三"));
    }
    @Test
    public void getterの動作() {
        assertThat(book.getAuthorNames()).isEqualTo("王陽明");
        assertThat(book.getTranslatorName()).isEqualTo("溝口雄三");
    }

}
