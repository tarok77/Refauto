package com.tarok.citationgenerator.Repository.ValueObjects.publishedyear;

import lombok.ToString;

/**
 * XMLから取得した値がvalueオブジェクトに変換できなかったとき、もとの情報をユーザに提示するために保持するクラス
 * Bookクラスに保持されうるようIsbnの実装クラスとする
 */
@ToString
public class SuspiciousPublishedYear implements PublishedYear {
    private final String information;

    public SuspiciousPublishedYear(String yearAndMonth) {
        this.information = yearAndMonth;
    }

    @Override
    public String getYear() {
        return information;
    }
}
