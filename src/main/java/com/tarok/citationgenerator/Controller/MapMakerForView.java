package com.tarok.citationgenerator.Controller;

import com.tarok.citationgenerator.Repository.Article;
import com.tarok.citationgenerator.Repository.Book;

import java.util.HashMap;
import java.util.Map;

/**
 * BookもしくはArticleオブジェクトの三つの引用スタイルへの変更をおこないすべてをMapに収納する。
 */
public class MapMakerForView {
    /**
     * Bookオブジェクトの三つの引用スタイルへの変更をおこないすべてをMapに収納する。
     * @param book　参考文献そのもの
     * @return Map<String, String> 三つの引用スタイルを乗せたMap keyはAPA, standard, SIST02の三つ
     */
    public static Map<String, String> makeReferenceStylesAndPutThemOnMap(Book book) {
        var bookMap = new HashMap<String, String>();
        var APABookInfo = book.convertAPAReference();
        var standardBookInfo = book.convertStandardReference();
        var ChicagoBookInfo = book.convertSIST02Reference();
        bookMap.put("APA", APABookInfo);
        bookMap.put("standard", standardBookInfo);
        bookMap.put("SIST02", ChicagoBookInfo);

        return bookMap;
    }

    /**
     * Articleオブジェクトの三つの引用スタイルへの変更をおこないすべてをMapに収納する。
     * @param article　参考文献そのもの
     * @return Map<String, String> 三つの引用スタイルを乗せたMap keyはAPA, standard, SIST02の三つ
     */
    public static Map<String, String> makeReferenceStylesAndPutThemOnMap(Article article) {
        var bookMap = new HashMap<String, String>();
        var APABookInfo = article.convertAPAReference();
        var standardBookInfo = article.convertStandardReference();
        var ChicagoBookInfo = article.convertSIST02Reference();
        bookMap.put("APA", APABookInfo);
        bookMap.put("standard", standardBookInfo);
        bookMap.put("SIST02", ChicagoBookInfo);

        return bookMap;
    }
}


