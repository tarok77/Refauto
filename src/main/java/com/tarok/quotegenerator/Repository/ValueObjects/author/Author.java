package com.tarok.quotegenerator.Repository.ValueObjects.author;

import lombok.Data;

@Data
public class Author {
    //TODO sortのためにおそらく読みの情報を漢字と別に保存する必要がある
    private final String author;

    private Author(String author) throws IllegalArgumentException {
        if(author.length() == 0){throw new IllegalArgumentException("作者の名前が空です");}
        //共著の場合かなり長くなるので注意
        if(author.length() > 50){throw new IllegalArgumentException("作者の名前が長すぎます。");}
        this.author = author;
    }

    public static Author nameOf(String name) {
        return new Author(name);
    }

    @Override
    public String toString() {
        return this.author;
    }
}
