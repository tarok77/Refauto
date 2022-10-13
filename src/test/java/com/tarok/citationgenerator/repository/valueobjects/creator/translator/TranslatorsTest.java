package com.tarok.citationgenerator.repository.valueobjects.creator.translator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SuppressWarnings("NonAsciiCharacters")
class TranslatorsTest {

    @Test
    void translatorsフィールドが空のリストを持つ時isEmptyがtrueをかえす() {
        var sut = new Translators();
        sut.setTranslators(new ArrayList<>());

        var actual = sut.isEmpty();

        assertThat(actual).isEqualTo(true);
    }
}