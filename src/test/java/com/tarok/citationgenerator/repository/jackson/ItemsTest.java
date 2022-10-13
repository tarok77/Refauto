package com.tarok.citationgenerator.repository.jackson;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SuppressWarnings("NonAsciiCharacters")
class ItemsTest {
    Items sut = new Items();

    @Test
    void getCreatorString() {
        sut.setCreator(List.of("G・E・M アンスコム", "吉田 廉", "京念屋 隆史"));

        var actual = sut.getCreatorString();

        assertThat(actual).isEqualTo("G・E・M アンスコム, 吉田 廉, 京念屋 隆史");
    }

    @Test
    void creatorフィールドがnullのときtestGetCreatorStringが呼ばれると空文字が返る(){
        var actual = sut.getCreatorString();

        assertThat(actual).isEqualTo("");
    }
}