package com.tarok.citationgenerator.Service.httpAccess;

import com.tarok.citationgenerator.Service.JudgeDataType.InputtedDataType;
import com.tarok.citationgenerator.Service.JudgeDataType.JudgeISBN;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 入力値を判定しapiに対するURLに変更する
 */
@Component
public class URLMaker {

    public String createURLByTitleAndAuthor(String title, String author) {
        String preEncodedQuery = "title=\"" + title +"\" AND creator=\"" + author + "\"";
        String query = URLEncoder.encode(preEncodedQuery, StandardCharsets.UTF_8);

        String url = "https:iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=30&recordSchema=dcndl" +
                "&onlyBib=true&recordPacking=xml&dpid=iss-ndl-opac&query=" + query ;

        return url;
    }

    public String createURLByISBN(String inputtedIsbn) {
        String query = inputtedIsbn;
        JudgeISBN judgeISBN = new JudgeISBN();
        String isbn = judgeISBN.normalizeByteType(inputtedIsbn);

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

        return url;
    }
}
