package com.tarok.citationgenerator.Repository.ValueObjects.creator.translator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TranslatorsTest {

    @Test
    void translatorsフィールドが空のリストを持つ時isEmptyがtrueをかえす() {
        var sut = new Translators();
        sut.setTranslators(new ArrayList<Translator>());

        var actual = sut.isEmpty();

        assertThat(actual).isEqualTo(true);
    }
}