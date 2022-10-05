package com.tarok.citationgenerator.Repository.ValueObjects;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VolumeAndNumTest {

    @Test
    void ofにNO_DATAが渡されたときvolumuとnumフィールドにNO_DATAがはいる() {
        var instance = VolumeAndNum.of("NO_DATA");

        assertThat(instance.getVolume()).isEqualTo("NO_DATA");
        assertThat(instance.getNum()).isEqualTo("NO_DATA");
    }

    @Test
    void ofに括弧付きのStringが渡されたとき巻と号に分離される() {
        var instance = VolumeAndNum.of("3(55)");

        assertThat(instance.getVolume()).isEqualTo("3");
        assertThat(instance.getNum()).isEqualTo("55");
    }
}