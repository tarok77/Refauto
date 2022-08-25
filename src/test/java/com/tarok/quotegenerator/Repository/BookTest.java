package com.tarok.quotegenerator.Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookTest {
    Book book = new Book();

    @BeforeEach
    public void setUpBook() {
        this.book.setAuthors("王陽明");
        this.book.setTitle("伝習録");
        this.book.setIsbn(new Isbn("9784121600820"));
        this.book.setPublisher("中央公論新社");
        this.book.setPublishedYear("2005");
        this.book.setTranslator("溝口雄三");
    }
    @Test
    public void getterの動作() {
        assertThat(book.getAuthorNames()).isEqualTo("王陽明");
        assertThat(book.getTranslator()).isEqualTo("溝口雄三");
    }

}
