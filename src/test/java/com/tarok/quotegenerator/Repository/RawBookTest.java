package com.tarok.quotegenerator.Repository;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class RawBookTest {

    @Test
    void formatによる著者と訳者の分割が正常に行われる() {
        RawBook sut = new RawBook();
        sut.addCreatorList("David Thomas, Andrew Hunt共著;  村上雅章訳");
        sut.divideAuthorAndTranslator();
        assertThat(sut.getCreatorList().get(0)).isEqualTo("David Thomas, Andrew Hunt共著");
        assertThat(sut.getCreatorList().get(1)).isEqualTo("村上雅章訳");
    }
}