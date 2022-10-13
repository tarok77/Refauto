package com.tarok.citationgenerator.repository.valueobjects.publishedyear;

import lombok.Data;

import java.time.Year;

/**
 * 論文に対応したところ年月日、年月、年のデータが混在していたため年のみの場合に対応するためのクラス。
 * YearクラスをPublishedYearとして扱うためのアダプター。
 */
@Data
public class OnlyYear implements PublishedYear{
    private Year year;

    public static OnlyYear of(String year) {
        var onlyYear = new OnlyYear();
        onlyYear.setYear(Year.of(Integer.parseInt(year)));

        return onlyYear;
    }

    @Override
    public String get() {
        return String.valueOf(year);
    }
}
