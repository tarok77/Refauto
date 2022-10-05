package com.tarok.citationgenerator.Repository.ValueObjects.publishedyear;

import lombok.ToString;

@ToString
public class NullPublishedYear implements PublishedYear{
    private final String noData = "NO_DATA";

    @Override
    public String get() {
        return "NO_DATA";
    }
}
