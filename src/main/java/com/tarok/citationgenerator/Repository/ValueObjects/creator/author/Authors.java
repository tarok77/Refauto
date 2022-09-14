package com.tarok.citationgenerator.Repository.ValueObjects.creator.author;

import com.tarok.citationgenerator.Repository.ExistDataType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data

public class Authors {
    private List<Author> authors;

    public Authors(Author author) {
        this.authors = List.of(author);
    }

    public Authors(List<String> authors) {
        this.authors = authors.stream().map(Author::nameOf).toList();
    }

    public Authors(String author) {
        this.authors = List.of(Author.nameOf(author));
    }

    //NPE対策にnullを渡されたときは空のリストを生成する
    public Authors() {this.authors = new ArrayList<>();}

    public String getAuthorsNames() {
        //リストが空であるときはデータがないことを伝える
        if(authors.isEmpty()) return "NO_DATA";

        String AuthorsNames = this.authors.stream().map(Author::getName)
                .reduce(" ", (a, b) -> a + ", " + b).replaceFirst(",", "").trim();
        return AuthorsNames;
    }

    //作者が三人以上であるとき最初の作者に「...他」をつけて代表させる方式で使う。
    public String getOrRepresent() {
        if(authors.isEmpty())return "NO_DATA";

        if(authors.size() > 2) {
            return authors.get(0).getName() + "ほか";
        }

        if(authors.size() > 1) {
            return authors.get(0).getName() + "・" + authors.get(1).getName();
        }

        return authors.get(0).getName();
    }



    public int size() {
        return authors.size();
    }

}
