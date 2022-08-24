package com.tarok.quotegenerator.Repository;

import lombok.Getter;

@Getter
public class Author {
    private final String author;

    public Author(String author) throws IllegalArgumentException {
        if(author.length() == 0){throw new IllegalArgumentException("作者の名前が空です");}
        //共著の場合かなり長くなるので注意
        if(author.length() > 50){throw new IllegalArgumentException("作者の名前が長すぎます。");}
        this.author = author;
    }

    @Override
    public String toString() {
        return this.author;
    }
}
