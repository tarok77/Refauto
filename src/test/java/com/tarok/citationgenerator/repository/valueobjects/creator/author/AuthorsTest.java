package com.tarok.citationgenerator.repository.valueobjects.creator.author;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class AuthorsTest {

    @Test
    void getAuthorsNamesメソッドによってauthorsフィールドの値が直結したStringが得られること() {
        //SetUp
        var sut = new Authors(List.of("Mike","Ichiro"));
        //Exercise
        String actual = sut.getAuthorsNames();
        //Assert
        assertThat(actual).isEqualTo("Mike・Ichiro");

    }

    @Test
    void authorsフィールドが空の時getAuthorsNamesメソッドによってNO_DATA文字列が返る() {
        //SetUp
        var sut = new Authors();
        //Exercise
        String actual = sut.getAuthorsNames();
        //Assert
        assertThat(actual).isEqualTo("NO_DATA");
    }

    @Test
    void reverseFirstName呼び出しでfirstnameが先頭に来る() {
        //Arrange
        var sut = new Authors(List.of("マイク　トラウト", "イチロー　スズキ"));
        //Act
        var result = sut.reverseFirstName();
        //Assert
        assertThat(result.getOrRepresent()).isEqualTo("トラウト, マイク・スズキ, イチロー");
    }
}