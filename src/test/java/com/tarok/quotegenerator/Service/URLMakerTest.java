package com.tarok.quotegenerator.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class URLMakerTest {
    URLMaker maker = new URLMaker();

    @Test
    public void 全角文字の半角化が行われる() {
        String sut = maker.normalizeTwoByte("１２３４５６７８９０");
        assertThat(sut).isEqualTo("1234567890");
    }

    @Test
    public void 全角数字以外が入力されたときにIllegalArgumentException() {
        assertThatThrownBy(() -> maker.normalizeTwoByte("1234567890"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("入力値が想定されていない値です。");
        assertThatThrownBy(() -> maker.normalizeTwoByte("１２３４５６７８９あ"))
                .isInstanceOf(IllegalArgumentException.class).hasMessage("入力値が想定されていない値です。");
    }
}