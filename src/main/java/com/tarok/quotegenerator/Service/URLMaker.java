package com.tarok.quotegenerator.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;

public class URLMaker {

    public String createURL(String inputtedString) {
        var judge = new JudgeIsbn();
        ISBNOrNotISBN result  = judge.judge(inputtedString);

        //違う場合はTitleとみなす
        if(result == ISBNOrNotISBN.NOTISBN) {
            return toKokkaiByTitle(inputtedString);
        }

        //半角であるときはそのままつかえる
        if(result == ISBNOrNotISBN.ONEBYTEISBN) {
            return toKokkaiByISBN(inputtedString);
        }

        //全角であるときは半角に正規化。十三桁の全角数字であることはすでに保証されている。
        //例外時はIllegalArgumentExceptionで停止
        if(result == ISBNOrNotISBN.TWOBYTEISBN){
            String normalizedISBN = normalizeTwoByte(inputtedString);
            return normalizedISBN;
        }

        throw new IllegalArgumentException("入力値が想定されていない値です。");
    }

    public String toKokkaiByISBN(String isbn) {
        String query = isbn ;
        //Todo maximumRecordは本番環境で要調整 ISBNに関しては理論上一件のみでいいはず
        String url = "https://iss.ndl.go.jp/api/opensearch?cnt=10&dpid=iss-ndl-opac&isbn=" + isbn ;

        return url;
    }

    public String toKokkaiByTitle(String title) {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        //Todo 同上
        String url = "https://iss.ndl.go.jp/api/opensearch?cnt=10&dpid=iss-ndl-opac&title=" + encodedTitle;
        System.out.println(url);

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
        for(int i = 0; i < twoByteNumber.length(); i++) {
            char c = twoByteNumber.charAt(i);
            if(c < '０' || c > '９'){
                throw new IllegalArgumentException("入力値が想定されていない値です。");
            }
            int c2 = c -  '０';
            builder.append(c2);
        }
        return builder.toString();
    }

}
