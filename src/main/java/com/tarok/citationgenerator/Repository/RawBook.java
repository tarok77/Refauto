package com.tarok.citationgenerator.Repository;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//XMLから抽出した未加工のStringを保存し、それをBook型に整形する　同一性によって重複したデータをふるいにかける役割もある
@Data
@NoArgsConstructor
//検索結果の重複排除用　ISBNだけでは使いまわしがあるため出版年月を含める
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RawBook {
    private String title;
    @EqualsAndHashCode.Include//翻訳者の情報の詳細さが違うことがありユーザーに選んでもらうためデータとして必要
    private List<String> creatorList = new ArrayList<>();
    @EqualsAndHashCode.Include
    private String publishedYearAndMonth;

    private String publisher;
    @EqualsAndHashCode.Include
    private String isbn;

    //コピーコンストラクタ
    public RawBook(RawBook rawBook) {
        this.title = rawBook.getTitle();
        this.creatorList = rawBook.getCreatorList();
        this.publishedYearAndMonth = rawBook.getPublishedYearAndMonth();
        this.publisher = rawBook.getPublisher();
        this.isbn = rawBook.getIsbn();
    }


    public boolean isNoData() {
        if (!(title == null)) return false;
        if (!(creatorList == null)) return false;
        if (!(publishedYearAndMonth == null)) return false;
        if (!(publisher == null)) return false;
        if (!(isbn == null)) return false;

        return true;
    }

    //情報の獲得に失敗した場合にNPEを避けるためのOptionalを返すゲッター群
    public Optional<String> getOptionalTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<String> getOptionalPublishedYear() {
        return Optional.ofNullable(publishedYearAndMonth);
    }

    public Optional<String> getOptionalPublisher() {
        return Optional.ofNullable(publisher);
    }

    public Optional<String> getOptionalIsbn() {
        return Optional.ofNullable(isbn);
    }

    public void addCreatorList(String creator) {
        creatorList.add(creator);
    }
}

