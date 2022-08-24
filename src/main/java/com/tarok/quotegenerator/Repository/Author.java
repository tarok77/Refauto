package com.tarok.quotegenerator.Repository;

import lombok.Getter;

@Getter
public class Author {
    private final String author;

    public Author(String author) throws IllegalArgumentException {
        if(author.length() == 0){throw new IllegalArgumentException("作者の名前が空です");}
        if(author.length() > 20){throw new IllegalArgumentException("作者の名前が長すぎます。");}
        this.author = author;
    }

    @Override
    public String toString() {
        return this.author;
    }
}
