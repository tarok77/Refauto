package com.tarok.citationgenerator.Repository;

import com.tarok.citationgenerator.Controller.BookForView;
import com.tarok.citationgenerator.Repository.ValueObjects.*;
import com.tarok.citationgenerator.Repository.ValueObjects.creator.author.Authors;
import com.tarok.citationgenerator.Repository.ValueObjects.creator.translator.Translators;
import com.tarok.citationgenerator.Repository.ValueObjects.isbn.Isbn;
import com.tarok.citationgenerator.Repository.ValueObjects.publishedyear.PublishedYear;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Data
@Component
public class Book {
    private Title title;

    private Authors authors;

    private PublishedYear publishedYear;

    private Publisher publisher;

    private Isbn isbn;

    private Translators translators;

    private boolean isTranslated;

    /**
     * スタティックファクトリメソッド
     * @param title　本のタイトル
     * @param creators　作者と翻訳者
     * @param publishedYear　出版年
     * @param publisher　出版社
     * @param isbn　ISBN
     * @return 生成されたBookインスタンス
     */
    public static Book of(String title, String creators, String publishedYear, String publisher, String isbn) {
        var book = new Book();
        book.setTranslated(false);

        var convertor = new CreatorsConverter();
        var creatorPair = convertor.convertFromString(creators);

        book.title = Title.nameOf(title);
        book.setCreators(creatorPair);
        book.publishedYear = PublishedYear.of(publishedYear);
        book.publisher = Publisher.nameOf(publisher);
        book.isbn = Isbn.numberOf(isbn);

        //翻訳書であるかどうかの判定のためtranslatorsが空か調べる
        if(!book.getTranslators().isEmpty()) {
            book.setTranslated(true);
        }
        return book;
    }

    /**
     * RawBookのもつフィールドデータのフォーマットを行った後それを使ってBookインスタンスを生成するファクトリメソッド
     * nullである場合は取得失敗をユーザーに伝えられる形に。
     *
     * @param raw 未加工の書籍データ
     * @return 加工後の書籍データ
     */
    public static Book format(RawBook raw) {
        var book = new Book();
        book.setTranslated(false);
        var creatorsConverter = new CreatorsConverter();

        book.setTitle(Title.nameOf(raw.getOptionalTitle().orElse("NO_DATA")));
        book.setCreators(creatorsConverter.convertRawBook(raw));
        book.setPublisher(new Publisher(raw.getOptionalPublisher().orElse("NO_DATA")));
        //以下二つ取得失敗時は空文字でnullオブジェクトを生成　想定外の値はユーザに確認を求めるためStringのまま転送
        book.setPublishedYear(PublishedYear.of(raw.getOptionalPublishedYear().orElse("NO_DATA")));
        book.setIsbn(Isbn.numberOf(raw.getOptionalIsbn().orElse("NO_DATA").replaceAll("-", "")));

        //翻訳書であるかどうかの判定のためtranslatorsが空か調べる
        if(!book.getTranslators().isEmpty()) {
            book.setTranslated(true);
        }
        return book;
    }

    /**
     * View用に変換されたオブジェクトであるBookForViewをBookオブジェクトに変換するスタティックファクトリメソッド
     * @param bookForView　XMLから変換されviewに提示されるために加工された本の情報
     * @return 新しく生成されたブックオブジェクト
     */
    public static Book fromBookForView(BookForView bookForView) {
        var book = new Book();
        var converter = new CreatorsConverter();

        book.setTitle(Title.nameOf(Objects.requireNonNullElse(bookForView.getTitle(),("NO_DATA"))));
        //空文字を与えれば空のリストを持つペアが生成され問題は起きない。
        book.setCreators(converter.convertFromString(Objects.requireNonNullElse(bookForView.getCreators(), (""))));
        //存在しない場合はnullオブジェクトの生成のため空文字を与える
        book.setPublishedYear(PublishedYear.of(Objects.requireNonNullElse(bookForView.getPublishedYear(), ("NO_DATA"))));
        book.setPublisher(Publisher.nameOf(Objects.requireNonNullElse(bookForView.getPublisher(),("NO_DATA"))));
        book.setIsbn(Isbn.numberOf(Objects.requireNonNullElse(bookForView.getIsbn(), ("NO_DATA"))));

        if(!book.translators.isEmpty()) {book.setTranslated(true);}
        return book;
    }
    public String getFullAuthorsNames() {
        if(!this.isTranslated) return authors.getAuthorsNames();

        Authors reversed = authors.reverseFirstName();
        return reversed.getAuthorsNames();
    }
    public String getOrRepresentAuthorsNames() {
        //翻訳書でない場合
        if(!this.isTranslated) return authors.getOrRepresent();
        //翻訳書である場合はreverseFirstNameで反転を試みる。漢字圏の著者は該当しないため影響は出ない。
        Authors reversed = authors.reverseFirstName();
        return reversed.getOrRepresent();
    }

    //TODO　要修正
    public String getTranslatorsNames() {
        return translators.getTranslatorsNames();
    }

    public void setAuthors(String author) {
        this.authors = new Authors(author);
    }


    public void setCreators(CreatorPair pair) {
        this.authors = new Authors(pair.getAuthors());
        this.translators = new Translators(pair.getTranslators());
    }

    public String convertAPAReference() {
        return getOrRepresentAuthorsNames() + "(" + publishedYear.get() + ")" +
                title.getWithDoubleBrackets() + translators.getTranslatorsNamesWithComma() + publisher.getName();
    }

    public String convertStandardReference() {
        if (!this.isTranslated) {
            return getOrRepresentAuthorsNames() + " " + title.getWithDoubleBrackets() +  " "
                    + publisher.getName() + "、 " + publishedYear.get() + "年。";
        }
        return getOrRepresentAuthorsNames() + " " + title.getWithDoubleBrackets() +  " " + translators.getTranslatorsNames() + "、 "
                + publisher.getName() + "、 " + publishedYear.get() + "年。";
    }

    public String convertSIST02Reference() {
        if(!this.isTranslated) {
            return getOrRepresentAuthorsNames() + ". " + title.get() + ". " + publisher.getName()
                    + ", " + publishedYear.get() + ".";
        }
        return getOrRepresentAuthorsNames() + ". " + title.get() + ". " +
                translators.getTranslatorsNames().replaceAll("、", ", ") + ". "
                + publisher.getName() + ", " + publishedYear.get() + ".";
    }
}
