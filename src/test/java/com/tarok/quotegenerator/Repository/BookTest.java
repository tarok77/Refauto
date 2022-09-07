package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.*;
import com.tarok.quotegenerator.Repository.ValueObjects.creator.translator.Translators;
import com.tarok.quotegenerator.Repository.ValueObjects.isbn.IsbnImpl;
import com.tarok.quotegenerator.Repository.ValueObjects.publishedyear.PublishedYearImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
public class BookTest {
    Book book = new Book();

    @BeforeEach
    public void setUpBook() {
        this.book.setAuthors("王陽明");
        this.book.setTitle(Title.nameOf("伝習録"));
        this.book.setIsbn(new IsbnImpl("9784121600820"));
        this.book.setPublisher(new Publisher("中央公論新社"));
        this.book.setPublishedYear(new PublishedYearImpl("2005.9"));
        this.book.setTranslators(new Translators("溝口雄三"));
    }
    @Test
    public void getterの動作() {
        assertThat(book.getAuthorNames()).isEqualTo("王陽明");
        assertThat(book.getTranslatorName()).isEqualTo(List.of("溝口雄三"));
    }

}
