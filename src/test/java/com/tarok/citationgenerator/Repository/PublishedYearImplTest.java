package com.tarok.citationgenerator.Repository;

import com.tarok.citationgenerator.Repository.ValueObjects.publishedyear.PublishedYearImpl;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class PublishedYearImplTest {
    @Test
    public void コンマ区切りの年月によるpublishedYearの生成が行われる() {
        var sut = new PublishedYearImpl("2010.10");
        assertThat(sut.getYearAndMonth()).isEqualTo(YearMonth.of(2010, 10));
        assertThat(sut.getYearAndMonth().getYear()).isEqualTo(2010);
        //日付が一文字の時のテスト
        var sut2 = new PublishedYearImpl("1997.9");
        assertThat(sut2.getYearAndMonth()).isEqualTo(YearMonth.of(1997, 9));
        assertThat(sut2.getMonth()).isEqualTo(9);
    }
    @Test
    public void 年月のgetが期待された値を返す() {
        var sut = new PublishedYearImpl("2010.12");
        assertThat(sut.getYear()).isEqualTo(2010);
        assertThat(sut.getMonth()).isEqualTo(12);

    }

}