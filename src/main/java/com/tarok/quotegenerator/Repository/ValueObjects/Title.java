package com.tarok.quotegenerator.Repository.ValueObjects;

import lombok.Data;

@Data
public class Title {
    private final String title;

    public Title(String title) {
        if(title.length()>100)throw new IllegalArgumentException("タイトルが長すぎます");
        this.title = title;
    }
}
