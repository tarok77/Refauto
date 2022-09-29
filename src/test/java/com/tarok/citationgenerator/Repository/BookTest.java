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
        this.sut.setTranslated(true);
    }
    @Test
    public void getAuthorsNamesによる著者の名前の獲得() {
        assertThat(sut.getOrRepresentAuthorsNames()).isEqualTo("王陽明");
    }
    @Test
    public void getTranslatorsNamesによる翻訳者の名前の獲得() {
        assertThat(sut.getTranslatorsNames()).isEqualTo("溝口雄三訳");
    }

    @Test
    //TODO　assertに変更
    void convertAPAReference() {
        String apaReference = sut.convertAPAReference();
        System.out.println(apaReference);
    }

    @Test
    void convertChicagoReference() {
        String chicagoReference = sut.convertSIST02Reference();
        System.out.println(chicagoReference);
    }

    @Test
    void convertMLAReference() {
        String MLAReference = sut.convertStandardReference();
        System.out.println(MLAReference);
    }

    @Test
    void getAuthorsNamesで漢字圏の著者が変更されないこと(){
        assertThat(sut.getOrRepresentAuthorsNames()).isEqualTo("王陽明");
    }
    @Test
    void getAuthorsNamesで西洋圏の著者名のfirstnameが先頭に来ること() {
        //Arrange
        sut.setAuthors("フィリップ ボール");
        //Act
        var actual = sut.getOrRepresentAuthorsNames();
        //Assert
        assertThat(actual).isEqualTo("ボール, フィリップ");

    }
    @Test
    void rawBookを受け取るスタティックファクトリメソッドの挙動確認() {
        //Arrange
        var target = new RawBook();
        target.setTitle("実践JUnit : 達人プログラマーのユニットテスト技法");
        target.setCreatorList(List.of("Jeff Langr", "Andy Hunt", "Dave Thomas著","牧野聡 訳"));
        //Act
        var instance = Book.format(target);
        //Assert
        assertThat(instance.getFullAuthorsNames()).isEqualTo("Langr, Jeff・Hunt, Andy・Thomas, Dave");
    }
}
