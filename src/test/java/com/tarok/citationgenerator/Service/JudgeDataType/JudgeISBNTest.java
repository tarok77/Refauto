package com.tarok.citationgenerator.Service.JudgeDataType;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


@SuppressWarnings("NonAsciiCharacters")
class JudgeISBNTest {
    JudgeISBN sut = new JudgeISBN();

    @Test
    public void normalizeTwoByteで全角文字の半角化が行われる() {
        String actual = sut.normalizeTwoByte("１２３４５６７８９０");
        assertThat(actual).isEqualTo("1234567890");
    }

    @Test
    public void normalizeTwoByteで全角数字以外が入力されたときにIllegalArgumentException() {
        assertThatThrownBy(() -> sut.normalizeTwoByte("1234567890"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("入力値が想定されていない値です。");
        assertThatThrownBy(() -> sut.normalizeTwoByte("１２３４５６７８９あ"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("入力値が想定されていない値です。");
    }

    @Test
    public void normalizeByteTypeにISBN以外のデータが与えられたときエラーを出す(){
        //Arrange
        String target = "123456789";
        String target2 = "weihfaweow";
        //Act and Assert
        assertThatThrownBy(() -> sut.normalizeByteType(target)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("isbnではありません");
        assertThatThrownBy(() -> sut.normalizeByteType(target2)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("isbnではありません");
    }
    @Test
    public void normalizeByteTypeに全角のデータが与えられたとき半角化される(){
        //Arrange
        String target = "１２３４５６７８９０";
        //Act
        var actual = sut.normalizeByteType(target);
        //Assert
        assertThat(actual).isEqualTo("1234567890");
    }
}