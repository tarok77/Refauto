package com.tarok.citationgenerator.Service.httpAccess;

import com.tarok.citationgenerator.Service.JudgeDataType.InputtedDataType;
import com.tarok.citationgenerator.Service.JudgeDataType.JudgeDataType;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 入力値を判定しURLに変更する
 */
public class URLMaker {
    /**
     * URLの作成を行う
     *
     * @param inputtedString ブラウザに入力されたデータ
     * @return apiに対するURL
     */
    //TODO assertionの追加とエラー生成を削除
    public String createURL(String inputtedString) {
        var judge = new JudgeDataType();
        InputtedDataType result = judge.judge(inputtedString);

        //違う場合はTitleとみなす
        if (result == InputtedDataType.NOT_ISBN) {
            return toKokkaiByTitle(inputtedString);
        }

        //半角であるときはそのままつかえる
        if (result == InputtedDataType.ONE_BYTE_ISBN) {
            return toKokkaiByISBN(inputtedString);
        }

        //全角であるときは半角に正規化。
        String normalizedISBN = normalizeTwoByte(inputtedString);
        return normalizedISBN;
    }

    public String toKokkaiByISBN(String isbn) {
        String query = isbn;
//        Todo maximumRecordは本番環境で要調整 ISBNに関しては理論上一件のみでいいはず　二件づつ帰ってくる isbnの桁数が変わるときのミスか

//        String url = "https://iss.ndl.go.jp/api/opensearch?cnt=10&dpid=iss-ndl-opac&isbn=" + isbn ;
        String url = "https:iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=3&recordSchema=dcndl" +
                "&onlyBib=true&recordPacking=xml&query=isbn=" + isbn + "&dpid=iss-ndl-opac";

        return url;
    }

    public String toKokkaiByTitle(String title) {
        String encodedTitle = URLEncoder.encode("=" + title, StandardCharsets.UTF_8);
        //Todo 同上
//        String url = "https://iss.ndl.go.jp/api/opensearch?cnt=10&dpid=iss-ndl-opac&title=" + encodedTitle;
        String url = "https:iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=20&recordSchema=dcndl" +
                "&onlyBib=true&recordPacking=xml&dpid=iss-ndl-opac&query=title" + encodedTitle;
        return url;
    }

    //作品名であるか作者の名であるかは判別不能のため　&　条件で使うためにつかうよてい。タイトルにプラスする形かな。
    public String toKokkaiByCreator(String creator) {
        String encodedQuery = URLEncoder.encode(creator, StandardCharsets.UTF_8);
        //Todo 同上
        String additionalUrl = "&creator=" + encodedQuery;

        return additionalUrl;
    }

    public String normalizeTwoByte(String twoByteNumber) {
        var builder = new StringBuilder();
        for (int i = 0; i < twoByteNumber.length(); i++) {
            char c = twoByteNumber.charAt(i);
            if (c < '０' || c > '９') {
                throw new IllegalArgumentException("入力値が想定されていない値です。");
            }
            int c2 = c - '０';
            builder.append(c2);
        }
        return builder.toString();
    }

}
