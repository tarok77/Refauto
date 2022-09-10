package com.tarok.citationgenerator.Repository.ValueObjects.translator;

import com.tarok.citationgenerator.Repository.ExistDataType;
import com.tarok.citationgenerator.Repository.ValueObjects.creator.translator.Translators;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TranslatorsTest {

    @Test
    void arePresent() {
        Translators sut = new Translators("ロバート・マーチン");
        assertThat(sut.arePresent()).isEqualTo(ExistDataType.SUSPICIOUS);
        sut = Translators.notExist();
        assertThat(sut.arePresent()).isEqualTo(ExistDataType.NOT_EXIST);
    }
}