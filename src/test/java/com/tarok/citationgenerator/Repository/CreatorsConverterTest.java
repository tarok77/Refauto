package com.tarok.citationgenerator.Repository;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class CreatorsConverterTest {

    @Test
    void convert() {
        var sut = new CreatorsConverter();
        var target = new RawBook();
        target.setCreatorList(List.of("David Thomas, Andrew Hunt共著 ; 村上雅章訳"));
        var result = sut.convertRawBook(target);

        assertThat(result.getAuthors().get(1)).isEqualTo("Andrew Hunt");
        assertThat(result.getTranslators().get(0)).isEqualTo("村上雅章");
    }

    @Test
    void divideによる著者と訳者の分割が正常に行われる() {
        var sut = new CreatorsConverter();
        var target = List.of("David Thomas, Andrew Hunt共著;  村上雅章訳");
        var result = sut.divideAuthorAndTranslator(target);
        assertThat(result.get(0)).isEqualTo("David Thomas, Andrew Hunt共著");
        assertThat(result.get(1)).isEqualTo("村上雅章訳");
    }
    @Test
    void divideにsplitのターゲットとなる正規表現が含まれていないとき動作し何の加工もしない() {
        var sut = new CreatorsConverter();
        var target = List.of("David Thomas, Andrew Hunt共著  村上雅章訳");
        var result = sut.divideAuthorAndTranslator(target);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).isEqualTo(List.of("David Thomas, Andrew Hunt共著  村上雅章訳"));
    }

    @Test
    void DivideCreatorsによるauthorの分割() {
        var target = List.of("David Thomas, Andrew Hunt共著, 村上正章訳");
        var result = CreatorsConverter.divideCreators(target);
        assertThat(result.get(0)).isEqualTo("David Thomas");
        assertThat(result.get(1)).isEqualTo("Andrew Hunt共著");
        assertThat(result.get(2)).isEqualTo("村上正章訳");
    }

    @Test
    void groupCreatorsによる著者と訳者の分類() {
        var sut = new CreatorsConverter();
        var target = List.of("David Thomas", "Andrew Hunt共著", "村上正章訳");
        var result = sut.groupCreators(target);
        assertThat(result.getAuthors().get(0)).isEqualTo("David Thomas");
        assertThat(result.getTranslators().get(0)).isEqualTo("村上正章");
    }
}