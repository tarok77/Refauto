package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.*;
import com.tarok.quotegenerator.Repository.ValueObjects.author.Authors;
import com.tarok.quotegenerator.Repository.ValueObjects.translator.Translators;
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
    public static Book format(RawBook raw) {
        var book = new Book();
        var creatorsConverter = new CreatorsConverter();
        book.setTitle(Title.nameOf(raw.getOptionalTitle().orElse("未発見")));
//        if(!raw.getCreatorList().isEmpty())book.setAuthors(raw.getCreatorList().get(0));
//        if(raw.getCreatorList().size()>=2)book.setTranslators(new Translators(raw.getCreatorList().get(1)));
//                else book.setTranslators(Translators.notExist());
        book.setCreators(creatorsConverter.convert(raw));
        book.setPublisher(new Publisher(raw.getOptionalPublisher().orElse("未発見")));
        //TODO 以下二つはおかしい
        book.setPublishedYear(new PublishedYear(raw.getOptionalPublishedYear().orElse("1111.1")));
        book.setIsbn(new Isbn(raw.getOptionalIsbn().orElse("1111111111").replaceAll("-", "")));
        return book;
    }

    public void setCreators(CreatorPair pair) {
        this.authors = new Authors(pair.getAuthors());
        this.translators = new Translators(pair.getTranslators());
    }

}
