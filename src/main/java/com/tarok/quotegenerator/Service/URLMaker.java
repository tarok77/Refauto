package com.tarok.quotegenerator.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLMaker {
    public String toKokkaiByISBN(String isbn) {
        String query = isbn ;
//        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        //Todo maximumRecordは本番環境で要調整 ISBNに関しては理論上一件のみでいいはず
        String url = "https://iss.ndl.go.jp/api/opensearch?cnt=10&dpid=iss-ndl-opac&isbn="
                //"https://iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=10&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query="
                + isbn ;
//                + " AND dpid=iss-ndl-opac";

        return url;
    }

    public String toKokkaiByTitle(String title) {
        String query = "title=\"" + title + "\"";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        //Todo 同上
        String url = "https://iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=10&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query="
                + encodedQuery +
                " AND dpid=iss-ndl-opac";

        return url;
    }

    public String toKokkaiByCreator(String creator) {
        String query = "creator=\"" + creator + "\"";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        //Todo 同上
        String url = "https://iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=10&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query="
                + encodedQuery +
                " AND dpid=iss-ndl-opac";

        return url;
    }
}
