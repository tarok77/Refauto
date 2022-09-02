package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.*;
import lombok.Data;
import org.springframework.stereotype.Component;

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
     * @param raw  未加工の書籍データ
     * @return 加工後の書籍データ
     */
    public static Book format(RawBook raw) {
        var book = new Book();
        book.setTitle(new Title(raw.getTitleString()));
//        book.setAuthors(new Authors(raw.getCreatorStringList().toString()));
//        book.setTranslator(new Translator(raw.getCreatorStringList()));
        book.setPublisher(new Publisher(raw.getPublisherSting()));
        book.setPublishedYear(new PublishedYear(raw.getPublishedYearString()));
        book.setIsbn(new Isbn(raw.getIsbnString().replaceAll("-", "")));
        return book;
    }

}
