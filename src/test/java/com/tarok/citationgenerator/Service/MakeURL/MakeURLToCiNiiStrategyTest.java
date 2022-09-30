package com.tarok.citationgenerator.Service.MakeURL;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SuppressWarnings("NonAsciiCharacters")
class MakeURLToCiNiiStrategyTest {

    @Test
    void makeURLByTitleで引数を含んだURLの生成() {
        var sut = new MakeURLToCiNiiStrategy();

        String actual = sut.makeURL(WithWhat.WITH_TITLE,"ヴァレリー");

        assertThat(actual).isEqualTo("https://cir.nii.ac.jp/opensearch/articles?title=ヴァレリー&count=30&sortorder=0&format=json");
    }

    @Test
    void makeURLにタイトルと作者が与えられたときのURLの生成() {
        var sut = new MakeURLToCiNiiStrategy();

        String actual = sut.makeURL(WithWhat.WHITH_TITLE_AND_AUTHOR,"陽明学","荒木" );

        assertThat(actual).isEqualTo("https://cir.nii.ac.jp/opensearch/articles?title=陽明学&creator=荒木&count=30&sortorder=0&format=json");
    }
}