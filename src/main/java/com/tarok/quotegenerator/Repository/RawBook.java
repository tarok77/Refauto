package com.tarok.quotegenerator.Repository;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//XMLから抽出した未加工のStringを保存し、それをBook型に整形する
@Data
@NoArgsConstructor
public class RawBook {
    private String title;

    private List<String> creatorList = new ArrayList<>();

    private String publishedYear;

    private String publisher;

    private String isbn;

    //コピーコンストラクタ
    public RawBook(RawBook rawBook) {
        this.title = rawBook.getTitle();
        this.creatorList = rawBook.getCreatorList();
        this.publishedYear = rawBook.getPublishedYear();
        this.publisher = rawBook.getPublisher();
        this.isbn = rawBook.getIsbn();
    }


    public boolean isNoData() {
        if (!(title == null)) return false;
        if (!(creatorList == null)) return false;
        if (!(publishedYear == null)) return false;
        if (!(publisher == null)) return false;
        if (!(isbn == null)) return false;

        return true;
    }

    //情報の獲得に失敗した場合にNPEを避けるため
    public Optional<String> getOptionalTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<String> getOptionalPublishedYear() {
        return Optional.ofNullable(publishedYear);
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

    //古いデータではDavid Thomas, Andrew Hunt共著 ; 村上雅章訳のように著者と訳者が同じタグ内に併記されるため両者を分ける
    //処理データの長さはせいぜい二三このはず
    public void divideAuthorAndTranslator() {
        if (this.creatorList.isEmpty()) return;

        //順番を維持したいので新しいリストを作って順に詰め直している。パフォーマンス次第で要検討 なにかあやしい
        List<String> alternative = new ArrayList<>();
        for (String name : creatorList) {
            //無関係の場合の早期処理　そのまま詰めなおす
            if (!name.contains(";")) {
                alternative.add(name);
                continue;
            }
            //著者と訳者が併記されているときのサイン　分割を行う
            int index = name.indexOf(";");
            String front = name.substring(0, index).trim();
            String end = name.substring(index + 1).trim();
            alternative.add(front);
            alternative.add(end);
        }
        this.creatorList = alternative;
    }
}

