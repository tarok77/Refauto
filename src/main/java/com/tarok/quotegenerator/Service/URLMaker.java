package com.tarok.quotegenerator.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLMaker {
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

    public String toKokkaiByCreator(String creator) {
        String encodedQuery = URLEncoder.encode(creator, StandardCharsets.UTF_8);
        //Todo 同上
        String url = "https://iss.ndl.go.jp/api/opensearch?cnt=10&dpid=iss-ndl-opac&creator=" + encodedQuery;

        return url;
    }

}
