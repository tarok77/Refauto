package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.PublishedYear;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class PublishedYearTest {
    @Test
    public void コンマ区切りの年月によるpublishedYearの生成が行われる() {
        var sut = new PublishedYear("2010.10");
        assertThat(sut.getYearAndMonth()).isEqualTo(LocalDate.of(2010, 10, 1));
        assertThat(sut.getYearAndMonth().getYear()).isEqualTo(2010);
        //日付が一文字の時のテスト
        var sut2 = new PublishedYear("1997.9");
        assertThat(sut2.getYearAndMonth()).isEqualTo(LocalDate.of(1997, 9, 1));
        assertThat(sut2.getMonth()).isEqualTo(9);
    }
    @Test
    public void 年月のgetが期待された値を返す() {
        var sut = new PublishedYear("2010.12");
        assertThat(sut.getYear()).isEqualTo(2010);
        assertThat(sut.getMonth()).isEqualTo(12);

    }

}