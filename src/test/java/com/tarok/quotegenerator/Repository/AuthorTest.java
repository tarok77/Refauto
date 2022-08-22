package com.tarok.quotegenerator.Repository;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AuthorTest {
    @Test
    public void 作者の名前が長すぎるときエラーが投げられる() {
        assertThatThrownBy(() -> new Author("じゅげむじゅげむごこうのすりきれかいじゃりすいぎょの")).isInstanceOf(IllegalArgumentException.class);
    }
}
