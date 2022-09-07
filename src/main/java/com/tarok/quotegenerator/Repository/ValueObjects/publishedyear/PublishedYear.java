package com.tarok.quotegenerator.Repository.ValueObjects.publishedyear;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;

public interface PublishedYear {
    static PublishedYear of(String yearAndMonth) {
        if (yearAndMonth.isBlank()) return new NullPublishedYear();

        try {
            return new PublishedYearImpl(yearAndMonth);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            return new SuspiciousPublishedYear(yearAndMonth);
        }

    }
}
