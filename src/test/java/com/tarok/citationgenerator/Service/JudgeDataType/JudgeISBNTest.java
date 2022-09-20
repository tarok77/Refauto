package com.tarok.citationgenerator.Service.JudgeDataType;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


@SuppressWarnings("NonAsciiCharacters")
class JudgeISBNTest {
    JudgeISBN sut = new JudgeISBN();

    @Test
    public void 全角文字の半角化が行われる() {
        String actual = sut.normalizeTwoByte("１２３４５６７８９０");
        assertThat(actual).isEqualTo("1234567890");
    }

    @Test
    public void 全角数字以外が入力されたときにIllegalArgumentException() {
        assertThatThrownBy(() -> sut.normalizeTwoByte("1234567890"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("入力値が想定されていない値です。");
        assertThatThrownBy(() -> sut.normalizeTwoByte("１２３４５６７８９あ"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("入力値が想定されていない値です。");
    }
}