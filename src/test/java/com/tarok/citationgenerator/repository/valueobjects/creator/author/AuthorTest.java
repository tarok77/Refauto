package com.tarok.citationgenerator.repository.valueobjects.creator.author;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
class AuthorTest {

    @Test
    void 空白もしくで区切られたの名前が与えられたときにgetReversedメソッドが名前を反転させる() {
        var sut = Author.nameOf("Chad Fowler");
        assertThat(sut.getReversed()).isEqualTo("Fowler, Chad");
    }

    @Test
    void 中黒で区切られた名前が与えられたときgetReversedメソッドが名前を反転させる() {
        var sut = Author.nameOf("エルンスト・H・ゴンブリッチ");
        assertThat(sut.getReversed()).isEqualTo("ゴンブリッチ, エルンスト H.");
    }

    @Test//
    void ミドルネームがある場合のgetReversedメソッドの動作() {
        var sut = Author.nameOf("アンジェラ E.マクホルム");
        var actual =sut.getReversed();
        assertThat(actual).isEqualTo("マクホルム, アンジェラ E.");
    }

    @Test
    public void 作者の名前が長すぎるときスタティックファクトリメソッドでエラーが投げられる() {
        assertThatThrownBy(() -> Author.nameOf("寿限無寿限無五劫のすりきれ海砂利水魚の水行末雲来末風来末食う寝るところに" +
                "住むところやぶらこうじのぶらこうじパイポパイポパイポのシューリンガンシューリンガンのグーリンダイグーリンダイの" +
                "ポンポコピーのポンポコナの長久命の長助")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 空文字が渡されたときスタティックファクトリメソッドでエラーが投げられる() {
        assertThatThrownBy(() -> Author.nameOf("")).isInstanceOf((IllegalArgumentException.class));
    }
}