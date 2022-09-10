package com.tarok.citationgenerator.Repository.ValueObjects;

import lombok.Data;

@Data
public class Publisher {
    private final String publisher;

    public Publisher(String publisher) {
        if(publisher.length()>30)throw new IllegalArgumentException();
        this.publisher = publisher;
    }
}
