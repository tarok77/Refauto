package com.tarok.citationgenerator.Repository.ValueObjects;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SuppressWarnings("NonAsciiCharacters")
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

    @Test
    void getVolumeAndNumInJapanese() {
        //Arrange
        var instance = VolumeAndNum.of("3(55)");
        //Act
        String actual = instance.getVolumeAndNumInJapanese();
        //Assert
        assertThat(actual).isEqualTo("3巻55号");
    }
    @Test
    void getメソッドで巻と号のフィールドが統一して表示される() {
        //Arrange
        var instance = VolumeAndNum.of("3(55)");
        //Act
        var actual = instance.get();
        //Assert
        assertThat(actual).isEqualTo("3(55)");
    }
}