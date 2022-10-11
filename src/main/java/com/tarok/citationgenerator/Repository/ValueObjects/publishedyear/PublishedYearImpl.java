package com.tarok.citationgenerator.Repository.ValueObjects.publishedyear;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

//出版年月を保存するためのクラス。Stringとして受けとったデータを整形しLocalDate型のフィールドとして保存する。YearMonthは継承不能なため委譲をつかう。
//TODO　和暦がありうる。
@Data
@Slf4j
public class PublishedYearImpl implements PublishedYear {
    //同著者による同じ年度の本を並べ替え可能にするために念のため月も保存するようにしておく。
    private final YearMonth YearAndMonth;
    public PublishedYearImpl(String yearAndMonth) {
        String tmp = yearAndMonth.replaceAll(" ", "").replaceAll("\\.|-","/");

        if(tmp.length() > 8) tmp = tmp.substring(0, 7);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/M");
        //TODO [19--]という値を持たされているとき変換エラー 不正値で止まらないようにスキップの実装　2014でも停止　1964年10月14日というのも
        //DateTimeParseExceptionの時何をするか
        this.YearAndMonth = YearMonth.parse(tmp, dtf);
    }

    public static PublishedYear of(String yearAndMonth) {
        return new PublishedYearImpl(yearAndMonth);
    }

    public String get() {
        return String.valueOf(YearAndMonth.getYear());
    }

    public String getMonth() {
        return String.valueOf(YearAndMonth.getMonthValue());
    }
}
