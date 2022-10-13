package com.tarok.citationgenerator.repository.valueobjects;

import lombok.Data;

@Data
public class Publisher {
    private final String publisherName;

    public Publisher(String publisherName) {
        if(publisherName.length()>30)throw new IllegalArgumentException();
        //元データに（発売）と含まれていることがあるのでそれを取り除いておく
        String replaced = publisherName.replaceAll("（|\\(|\\)|）|発売","").trim();
        this.publisherName = replaced;
    }

    public String getName() {
        return this.publisherName;
    }

    public static Publisher nameOf(String name) {
        return new Publisher(name);
    }
}
