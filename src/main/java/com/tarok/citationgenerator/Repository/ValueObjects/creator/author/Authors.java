package com.tarok.citationgenerator.Repository.ValueObjects.creator.author;

import com.tarok.citationgenerator.Repository.ExistDataType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Authors {
    private List<Author> authors;
    ExistDataType dataType;

    public Authors(Author author) {
        this.authors = List.of(author);
    }
    public Authors(ExistDataType dataType) {
        if(dataType.equals(ExistDataType.NOT_EXIST)) throw new IllegalArgumentException("この操作は認められません");

        this.dataType = ExistDataType.NOT_EXIST;
        this.authors = new ArrayList<>();
    }
    public Authors(List<String> authors) {
        this.authors = authors.stream().map(Author::nameOf).toList();
    }

    public Authors(String author) {
        this.authors = List.of(Author.nameOf(author));
    }

    //作者が三人以上であるとき最初の作者に「...他」をつけて代表させる。
    public String getOrRepresent() {
        if(authors.isEmpty())return "NO_DATA";

        if(authors.size() > 2) {
            return authors.get(0).toString() + "他";
        }

        if(authors.size() > 1) {
            return authors.get(0).toString() + "・" + authors.get(1).toString();
        }

        return authors.get(0).toString();
    }

    //データが見つからなかったとき
    public static Authors notExist() {
        return new Authors(ExistDataType.NOT_EXIST);
    }

    public int size() {
        return authors.size();
    }

    public void add(String author) {
        authors.add(Author.nameOf(author));
    }

}
