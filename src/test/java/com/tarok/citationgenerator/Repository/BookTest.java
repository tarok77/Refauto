package com.tarok.citationgenerator.Repository;

import com.tarok.citationgenerator.Repository.ValueObjects.*;
import com.tarok.citationgenerator.Repository.ValueObjects.creator.translator.Translators;
import com.tarok.citationgenerator.Repository.ValueObjects.isbn.IsbnImpl;
import com.tarok.citationgenerator.Repository.ValueObjects.publishedyear.PublishedYearImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
public class BookTest {
    Book sut = new Book();

    @BeforeEach
    public void setUpBook() {
        this.sut.setAuthors("王陽明");
        this.sut.setTitle(Title.nameOf("伝習録"));
        this.sut.setIsbn(new IsbnImpl("9784121600820"));
        this.sut.setPublisher(new Publisher("中央公論新社"));
        this.sut.setPublishedYear(new PublishedYearImpl("2005.9"));
        this.sut.setTranslators(new Translators("溝口雄三"));
    }
    @Test
    public void getAuthorsNamesによる著者の名前の獲得() {
        assertThat(sut.getAuthorsNames()).isEqualTo("王陽明");
    }
    @Test
    public void getTranslatorsNamesによる翻訳者の名前の獲得と読点の付与() {
        assertThat(sut.getTranslatorsNames()).isEqualTo("溝口雄三訳、");
    }

    @Test
    void convertAPAReference() {
        String apaReference = sut.convertAPAReference();
        System.out.println(apaReference);
    }

    @Test
    void convertChicagoReference() {
        String chicagoReference = sut.convertChicagoReference();
        System.out.println(chicagoReference);
    }

    @Test
    void convertMLAReference() {
        String MLAReference = sut.convertMLAReference();
        System.out.println(MLAReference);
    }
}
