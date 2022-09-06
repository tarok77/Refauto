package com.tarok.quotegenerator.Repository.ValueObjects;

import lombok.Data;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

//出版年月を保存するためのクラス。Stringとして受けとったデータを整形しLocalDate型のフィールドとして保存する。YearMonthは継承不能なため委譲をつかう。
//TODO　和暦がありうる。
@Data
public class PublishedYear {
    //同著者による同じ年度の本を並べ替え可能にするために念のため月も保存するようにしておく。
    private final YearMonth YearAndMonth;
    public PublishedYear(String yearAndMonth) {
        //一個にまとめる
        String tmp = yearAndMonth.replaceAll(" ", "").replaceAll("\\.","/");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/M");
        this.YearAndMonth = YearMonth.parse(tmp, dtf);
    }

    public int getYear() {
        return YearAndMonth.getYear();
    }

    public int getMonth() {
        return YearAndMonth.getMonthValue();
    }
}
