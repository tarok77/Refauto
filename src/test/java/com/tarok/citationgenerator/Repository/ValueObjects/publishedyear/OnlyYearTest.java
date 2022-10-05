package com.tarok.citationgenerator.Repository.ValueObjects.publishedyear;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OnlyYearTest {

    @Test
    void ofにStingが与えられたときのインスタンスの生成() {
        var instance = OnlyYear.of("1994");
        assertThat(instance).isInstanceOf(OnlyYear.class);
    }

    @Test
    void getYearにるストリングに変換された戻り値の確認() {
        //Arrange
        var instance = OnlyYear.of("1994");
        //Act & Assert
        assertThat(instance.get()).isEqualTo("1994");

    }
}