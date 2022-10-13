package com.tarok.citationgenerator.repository.valueobjects;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SuppressWarnings("NonAsciiCharacters")
class PublicationNameTest {

    @Test
    void deleteAfterEqualにイコールを含むフィールドが渡されたときイコール以降が削除されたStringが返る() {
        //Arrange
        var sut = PublicationName.of("日本経済研究 = JCER economic journal");
        //Act
        String actual = sut.getDeletedAfterEqual();
        //Assert
        assertThat(actual).isEqualTo("日本経済研究");
    }
}