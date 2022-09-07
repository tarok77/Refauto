package com.tarok.quotegenerator.Repository.ValueObjects.publishedyear;

import lombok.ToString;

@ToString
public class NullPublishedYear implements PublishedYear{
    private final String noData = "NO_DATA";
}
