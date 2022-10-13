package com.tarok.citationgenerator.repository.valueobjects.isbn;

import lombok.ToString;

@ToString
public class NullIsbn implements Isbn{
    private final String noData = "NO_DATA";

    @Override
    public String getIsbn() {
        return this.noData;
    }
}
