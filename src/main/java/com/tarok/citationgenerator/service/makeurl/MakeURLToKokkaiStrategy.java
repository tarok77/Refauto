package com.tarok.citationgenerator.service.makeurl;

import com.tarok.citationgenerator.service.judgeisbntype.JudgeISBN;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MakeURLToKokkaiStrategy implements MakeURLStrategy {
    @Override
    public String makeURL(With dataType, String searchInfo) {
        if (dataType == With.TITLE) {
            String encodedTitle = URLEncoder.encode("=" + searchInfo, StandardCharsets.UTF_8);

            String url = "https:iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=30&recordSchema=dcndl" +
                    "&onlyBib=true&recordPacking=xml&dpid=iss-ndl-opac&query=title" + encodedTitle;
            return url;
        }

        if (dataType == With.AUTHOR) {
            String encodedAuthor = URLEncoder.encode("=" + searchInfo, StandardCharsets.UTF_8);

            String url = "https:iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=30&recordSchema=dcndl" +
                    "&onlyBib=true&recordPacking=xml&dpid=iss-ndl-opac&query=creator" + encodedAuthor;

            return url;
        }

        JudgeISBN judgeISBN = new JudgeISBN();
        String isbn = judgeISBN.normalizeByteType(searchInfo);

        String url = "https:iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=3&recordSchema=dcndl" +
                "&onlyBib=true&recordPacking=xml&dpid=iss-ndl-opac&query=isbn=" + isbn;

        return url;

    }

    @Override
    public String makeURL(With dataType, String title, String author) {
        String preEncodedQuery = "title=\"" + title +"\" AND creator=\"" + author + "\"";
        String query = URLEncoder.encode(preEncodedQuery, StandardCharsets.UTF_8);

        String url = "https:iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=30&recordSchema=dcndl" +
                "&onlyBib=true&recordPacking=xml&dpid=iss-ndl-opac&query=" + query ;

        return url;
    }
}
