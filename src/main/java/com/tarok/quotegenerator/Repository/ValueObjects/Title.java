package com.tarok.quotegenerator.Repository.ValueObjects;

import lombok.Getter;

@Getter
public class Title {
    private final String title;

    public Title(String title) {
        if(title.length()>100)throw new IllegalArgumentException("タイトルが長すぎます");
        this.title = title;
    }
}
