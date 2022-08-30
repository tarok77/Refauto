package com.tarok.quotegenerator.Repository;

import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//出版年月を保存するためのクラス。Stringとして受けとったデータを整形しLocalDate型のフィールドとして保存する。LocalDate型を
//使用したいためにダミーの日付を使用しているがマナーとして許されるのか。要検討。Month型を使って自分で実装するか。
//TODO　和暦がありうる。恐るべきことに dc:dateとpubDateの値が異なっていることもある。要検証
//例:井上博士講論集 第1,2は明27,28 <pubDate>Thu, 30 Sep 1993 09:00:00 +0900</pubDate>と表記される
@Getter
public class PublishedYear {
    //同著者による同じ年度の本を並べ替え可能にするために念のため月も保存するようにしておく。
    private final LocalDate YearAndMonth;

    public PublishedYear(String yearAndMonth) {
        String tmp = yearAndMonth.replaceAll(" ", "").replaceAll("\\.","/");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/M/dd");
        LocalDate publishedYear = LocalDate.parse(tmp + "/01", dtf);

        this.YearAndMonth = publishedYear;
    }

    public int getYear() {
        return YearAndMonth.getYear();
    }

    public int getMonth() {
        return YearAndMonth.getMonthValue();
    }
}
