package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.*;
import com.tarok.quotegenerator.Repository.ValueObjects.translator.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
public class BookTest {
    Book book = new Book();

    @BeforeEach
    public void setUpBook() {
        this.book.setAuthors("王陽明");
        this.book.setTitle(Title.nameOf("伝習録"));
        this.book.setIsbn(new Isbn("9784121600820"));
        this.book.setPublisher(new Publisher("中央公論新社"));
        this.book.setPublishedYear(new PublishedYear("2005.9"));
        this.book.setTranslator(Translator.nameOf("溝口雄三"));
    }
    @Test
    public void getterの動作() {
        assertThat(book.getAuthorNames()).isEqualTo("王陽明");
        assertThat(book.getTranslatorName()).isEqualTo("溝口雄三");
    }

}
