package com.tarok.citationgenerator.controller.form;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
@SuppressWarnings("NonAsciiCharacters")
class TitleAndAuthorTest {

    @Test
    void isFilledEitherOneにどちらも空白が与えられたときfalseが返る() {
        //Arrange
        var sut = new TitleAndAuthor();
        sut.setTitle(" ");
        sut.setAuthor(" ");
        //Act
        boolean actual = sut.isFilledEitherOne();
        //Assert
        assertThat(actual).isFalse();
    }

    @Test
    void isFilledEitherOneのタイトルが埋まっているならばTrueが返る() {
        //Arrange
        var sut = new TitleAndAuthor();
        sut.setTitle("テスト");
        sut.setAuthor(" ");
        //Act
        boolean actual = sut.isFilledEitherOne();
        //Assert
        assertThat(actual).isTrue();
    }

    @Test
    void isFilledEitherOneの作者が埋まっているならばTrueが返る() {
        //Arrange
        var sut = new TitleAndAuthor();
        sut.setTitle("　");
        sut.setAuthor("John");
        //Act
        boolean actual = sut.isFilledEitherOne();
        //Assert
        assertThat(actual).isTrue();
    }
}