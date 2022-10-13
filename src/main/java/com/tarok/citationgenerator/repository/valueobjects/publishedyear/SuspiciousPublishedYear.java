package com.tarok.citationgenerator.repository.valueobjects.publishedyear;

import lombok.ToString;

/**
 * XMLまたはjsonから取得した値がvalueオブジェクトに変換できなかったとき、もとの情報をユーザに提示するために保持するクラス
 * Bookクラスに保持されうるようIsbnの実装クラスとする
 */
@ToString
public class SuspiciousPublishedYear implements PublishedYear {
    private final String information;

    public SuspiciousPublishedYear(String yearAndMonth) {
        this.information = yearAndMonth;
    }

    @Override
    public String get() {
        return information + "### データの形式が想定外です、修正してお使いください。###";
    }
}
