package com.tarok.citationgenerator.Repository;

import com.tarok.citationgenerator.Controller.BookForView;
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
    void convertAPAReference() {
        //Act
        String actual = sut.convertAPAReference();
        //Assert
        assertThat(actual).isEqualTo("王陽明(2005)『伝習録』溝口雄三訳、中央公論新社");
    }

    @Test
    void convertStandardReference() {
        //Act
        String actual = sut.convertStandardReference();
        //Assert
        assertThat(actual).isEqualTo("王陽明 『伝習録』 溝口雄三訳、 中央公論新社、 2005年。");
    }

    @Test
    void convertSIST02Reference() {
        //Act
        String actual = sut.convertSIST02Reference();
        //Assert
        assertThat(actual).isEqualTo("王陽明. 伝習録. 溝口雄三訳. 中央公論新社, 2005.");
    }


    @Test
    void getAuthorsNamesで漢字圏の著者が変更されないこと() {
        //Act
        var actual = sut.getOrRepresentAuthorsNames();
        //Assert
        assertThat(actual).isEqualTo("王陽明");
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
        target.setCreatorList(List.of("Jeff Langr", "Andy Hunt", "Dave Thomas著", "牧野聡 訳"));
        //Act
        var instance = Book.format(target);
        //Assert
        assertThat(instance.getFullAuthorsNames()).isEqualTo("Langr, Jeff・Hunt, Andy・Thomas, Dave");
    }

    @Test
    void fromBookForViewによるインスタンスの生成翻訳書の場合() {
        //Arrange
        BookForView target = new BookForView();
        target.setTitle("自然権と歴史");
        target.setCreators("レオ・シュトラウス著,塚崎智,石崎嘉彦共訳");
        target.setPublishedYear("2013/12");
        target.setPublisher("筑摩書房");
        target.setIsbn("1920110015000");
        var instance = Book.fromBookForView(target);
        //Act
        var actual = instance.isTranslated();
        //Assert
        assertThat(actual).isEqualTo(true);

    }

    @Test
    void fromBookForViewによるインスタンスの生成和書の場合() {
        //Arrange
        BookForView target = new BookForView();
        target.setTitle("アルゴリズムとデータ構造");
        target.setCreators("大槻兼資,秋葉拓哉");
        target.setPublishedYear("2020");
        target.setPublisher("講談社");
        target.setIsbn("1923055030008");
        var instance = Book.fromBookForView(target);
        //Act
        var actual = instance.isTranslated();
        //Assert
        assertThat(actual).isEqualTo(false);
    }
}
