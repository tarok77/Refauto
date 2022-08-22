package com.tarok.quotegenerator.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLMaker {
    public String toKokkaiByISBN(String isbn) {
        String query = "isbn=\"" + isbn + "\"";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        //maximumRecordは本番環境で要調整
        String url = "https://iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=10&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query="
                + encodedQuery +
                " AND dpid=iss-ndl-opac";

        return url;
    }

    public String toKokkaiByTitle(String title) {
        String query = "title=\"" + title + "\"";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        //maximumRecordは本番環境で要調整
        String url = "https://iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=10&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query="
                + encodedQuery +
                " AND dpid=iss-ndl-opac";

        return url;
    }

    public String toKokkaiByCreator(String creator) {
        String query = "creator=\"" + creator + "\"";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        //maximumRecordは本番環境で要調整
        String url = "https://iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=10&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query="
                + encodedQuery +
                " AND dpid=iss-ndl-opac";

        return url;
    }
}
