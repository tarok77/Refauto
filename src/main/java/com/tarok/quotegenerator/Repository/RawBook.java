package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//XMLから抽出した未加工のStringを保存し、それをBook型に整形する
@Data
@NoArgsConstructor
public class RawBook {
    private String titleString;

    private List<String> creatorStringList = new ArrayList<>();

    private String publishedYearString;

    private String publisherSting;

    private String isbnString;

    //コピーコンストラクタ
    public RawBook(RawBook rawBook) {
        this.titleString = rawBook.getTitleString();
        this.creatorStringList = rawBook.getCreatorStringList();
        this.publishedYearString = rawBook.getPublishedYearString();
        this.publisherSting = rawBook.getPublisherSting();
        this.isbnString = rawBook.getIsbnString();
    }


    public RawBook setDefault() {
        titleString = "default";
        //TODO 要変更
        publishedYearString = "1977.7";
        publisherSting = "default";
        isbnString ="1111111111";

        return this;
    }

    public boolean isNoData() {
        if(!(titleString==null))return false;
        if(!(creatorStringList==null))return false;
        if(!(publishedYearString==null))return false;
        if(!(publisherSting==null))return false;
        if(!(isbnString==null))return false;

        return true;
    }

    public void addCreatorStringList(String creator) {
        creatorStringList.add(creator);
    }
}
