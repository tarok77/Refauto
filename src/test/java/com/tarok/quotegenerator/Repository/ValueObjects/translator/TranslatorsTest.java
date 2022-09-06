package com.tarok.quotegenerator.Repository.ValueObjects.translator;

import com.tarok.quotegenerator.Repository.ExistDataType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TranslatorsTest {

    @Test
    void arePresent() {
        Translators sut = new Translators("ロバート・マーチン");
        assertThat(sut.arePresent()).isEqualTo(ExistDataType.SUSPICIOUS);
        sut = Translators.notExist();
        assertThat(sut.arePresent()).isEqualTo(ExistDataType.NOT_EXIST);
    }
}