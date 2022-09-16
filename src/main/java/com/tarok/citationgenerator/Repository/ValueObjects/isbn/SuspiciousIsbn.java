package com.tarok.citationgenerator.Repository.ValueObjects.isbn;

import lombok.ToString;

/**
 * XMLから取得した値がvalueオブジェクトに変換できなかったとき、もとの情報をユーザに提示するために保持するクラス
 * Bookクラスに保持されうるようIsbnの実装クラスとする
 */
@ToString
public class SuspiciousIsbn implements Isbn{
    private final String information;

    public SuspiciousIsbn(String number) {
        this.information = number;
    }
    public SuspiciousIsbn(long number) {
        this.information = String.valueOf(number);
    }

    @Override
    public String getIsbn() {
        return information + "### データの形式が想定外です。###";
    }
}
