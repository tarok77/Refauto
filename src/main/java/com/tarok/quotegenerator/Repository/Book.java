package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.*;
import com.tarok.quotegenerator.Repository.ValueObjects.creator.author.Authors;
import com.tarok.quotegenerator.Repository.ValueObjects.creator.translator.Translators;
import com.tarok.quotegenerator.Repository.ValueObjects.isbn.Isbn;
import com.tarok.quotegenerator.Repository.ValueObjects.isbn.IsbnImpl;
import com.tarok.quotegenerator.Repository.ValueObjects.publishedyear.PublishedYear;
import com.tarok.quotegenerator.Repository.ValueObjects.publishedyear.PublishedYearImpl;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Book {
    private Title title;

    private Authors authors;

    private PublishedYear publishedYear;

    private Publisher publisher;

    //情報の取得法によってはnullになってしまう可能性がある。entityのIDにはできないかな。
    private Isbn isbn;
    //nullでありうる
    private Translators translators;

    public String getAuthorNames() {
        return authors.getOrRepresentAuthorsName();
    }
    //TODO　要修正
    public List<String> getTranslatorName() {
        return translators.getTranslatorNames();
    }

    public void setAuthors(String author) {
        this.authors = new Authors(author);
    }

    /**
     * RawBookのもつフィールドデータのフォーマットを行った後それを使ってBookインスタンスを生成するファクトリメソッド
     * nullである場合は取得失敗をユーザーに伝えられる形に。
     * @param raw  未加工の書籍データ
     * @return 加工後の書籍データ
     */
    //TODO ヴァリューオブジェクト生成失敗時停止ではなくスキップして作業を進めさせる
    public static Book format(RawBook raw) {
        var book = new Book();
        var creatorsConverter = new CreatorsConverter();
        book.setTitle(Title.nameOf(raw.getOptionalTitle().orElse("未発見")));
        book.setCreators(creatorsConverter.convert(raw));
        book.setPublisher(new Publisher(raw.getOptionalPublisher().orElse("未発見")));
        //以下二つ取得失敗時は空文字でnullオブジェクトを生成　想定外の値はユーザに確認を求めるためStringのまま転送
        book.setPublishedYear(PublishedYear.of(raw.getOptionalPublishedYear().orElse("")));
        book.setIsbn(Isbn.numberOf(raw.getOptionalIsbn().orElse("").replaceAll("-", "")));
        return book;
    }

    public void setCreators(CreatorPair pair) {
        this.authors = new Authors(pair.getAuthors());
        this.translators = new Translators(pair.getTranslators());
    }

}
