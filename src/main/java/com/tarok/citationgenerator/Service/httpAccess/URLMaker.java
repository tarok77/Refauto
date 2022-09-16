package com.tarok.citationgenerator.Service.httpAccess;

import com.tarok.citationgenerator.Service.JudgeDataType.InputtedDataType;
import com.tarok.citationgenerator.Service.JudgeDataType.JudgeDataType;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 入力値を判定しapiに対するURLに変更する
 */
public class URLMaker {

    //TODO assertionの追加とエラー生成を削除
    public String normalizeByteType(String inputtedString) {
        var judge = new JudgeDataType();
        InputtedDataType result = judge.judge(inputtedString);

        //違う場合はTitleとみなす
        if (result == InputtedDataType.NOT_ISBN) {
            //エラーにしてもいい
            throw new IllegalArgumentException("isbnではありません");
        }

        //半角であるときはそのままつかえる
        if (result == InputtedDataType.ONE_BYTE_ISBN) {
            return inputtedString;
        }

        //全角であるときは半角に正規化。
        String normalizedISBN = normalizeTwoByte(inputtedString);
        return normalizedISBN;
    }

    public String createURLByTitleAndAuthor(String title, String author) {
        String preEncodedQuery = "title=\"" + title +"\" AND creator=\"" + author + "\"";
        String query = URLEncoder.encode(preEncodedQuery, StandardCharsets.UTF_8);

        String url = "https:iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=30&recordSchema=dcndl" +
                "&onlyBib=true&recordPacking=xml&dpid=iss-ndl-opac&query=" + query ;

        return url;
    }

    public String createURLByISBN(String inputtedIsbn) {
        String query = inputtedIsbn;
        String isbn = normalizeByteType(inputtedIsbn);

        String url = "https:iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=3&recordSchema=dcndl" +
                "&onlyBib=true&recordPacking=xml&dpid=iss-ndl-opac&query=isbn=" + isbn ;

        return url;
    }

    public String createURLByTitle(String title) {
        String encodedTitle = URLEncoder.encode("=" + title, StandardCharsets.UTF_8);

        String url = "https:iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=30&recordSchema=dcndl" +
                "&onlyBib=true&recordPacking=xml&dpid=iss-ndl-opac&query=title" + encodedTitle;
        return url;
    }

    public String CreateURLByAuthor(String author) {
        String encodedAuthor = URLEncoder.encode("=" + author, StandardCharsets.UTF_8);

        String url = "https:iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=30&recordSchema=dcndl" +
                "&onlyBib=true&recordPacking=xml&dpid=iss-ndl-opac&query=creator" + encodedAuthor;
        //String additionalUrl = "&creator=" + encodedQuery;

        return url;
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
