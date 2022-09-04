package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.*;
import com.tarok.quotegenerator.Repository.ValueObjects.translator.Translator;
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
    private Translator translator;

    public String getAuthorNames() {
        return authors.getOrRepresentAuthorsName();
    }
    public String getTranslatorName() {
        return translator.toString();
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
    public static Book format(RawBook raw) {
        var book = new Book();
        book.setTitle(Title.nameOf(raw.getOptionalTitle().orElse("未発見")));
        if(!raw.getCreatorList().isEmpty())book.setAuthors(raw.getCreatorList().get(0));
        if(raw.getCreatorList().size()>=2)book.setTranslator(Translator.nameOf(raw.getCreatorList().get(1)));
        book.setPublisher(new Publisher(raw.getOptionalPublisher().orElse("未発見")));
        //TODO 以下二つはおかしい
        book.setPublishedYear(new PublishedYear(raw.getOptionalPublishedYear().orElse("1111.1")));
        book.setIsbn(new Isbn(raw.getOptionalIsbn().orElse("1111111111").replaceAll("-", "")));
        return book;
    }
   //TODO ここから
//    public static Authors formatAuthors(List<String> creators) {
//
//    }

}
