package com.tarok.citationgenerator.Repository;

import com.tarok.citationgenerator.Repository.ValueObjects.*;
import com.tarok.citationgenerator.Repository.ValueObjects.creator.author.Authors;
import com.tarok.citationgenerator.Repository.ValueObjects.creator.translator.Translators;
import com.tarok.citationgenerator.Repository.ValueObjects.isbn.Isbn;
import com.tarok.citationgenerator.Repository.ValueObjects.publishedyear.PublishedYear;
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
        return authors.getOrRepresent();
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
     *
     * @param raw 未加工の書籍データ
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
    //TODO 要追加

    public static Book of(String title, String creators, String publishedYear, String publisher, String isbn) {
        var book = new Book();
        book.title = Title.nameOf(title);
//        book.authors = Authors.notExist();
        //book.translator = ;
        book.publishedYear = PublishedYear.of(publishedYear);
        book.publisher = Publisher.nameOf(publisher);
        book.isbn = Isbn.numberOf(isbn);

        return book;
    }

    public String convertAPAReference() {
        return authors.getOrRepresent() + "(" + publishedYear.getYear() + ")." +
                title.getWithBrackets() + translators.toString() + "、" + publisher.getName();
    }

    public String convertChicagoReference() {
        return authors.getOrRepresent() + title.getWithBrackets() + "(" +
                translators.toString() + ")" + publisher.getName() + "、" + publishedYear.getYear();
    }

    public String convertMLAReference() {
        return authors.getOrRepresent() + "、" + title.getWithBrackets() + "、" +
                translators.toString() + "、" + publisher.getName() + "、" + publishedYear.getYear() + "年。";
    }
}
