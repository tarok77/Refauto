package com.tarok.citationgenerator.Repository.ValueObjects;

import lombok.Data;

@Data
public class Publisher {
    private final String publisherName;

    public Publisher(String publisherName) {
        if(publisherName.length()>30)throw new IllegalArgumentException();
        this.publisherName = publisherName;
    }

    public String getName() {
        return publisherName;
    }

    public static Publisher nameOf(String name) {
        return new Publisher(name);
    }
}
