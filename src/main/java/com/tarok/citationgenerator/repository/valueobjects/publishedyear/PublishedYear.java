package com.tarok.citationgenerator.repository.valueobjects.publishedyear;

import java.time.format.DateTimeParseException;

public interface PublishedYear {
    static PublishedYear of(String yearAndMonth) {
        if (yearAndMonth.isBlank() || yearAndMonth.equals("NO_DATA")) return new NullPublishedYear();

        try {
            if(yearAndMonth.length() == 4) return OnlyYear.of(yearAndMonth);

            return new PublishedYearImpl(yearAndMonth);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            return new SuspiciousPublishedYear(yearAndMonth);
        }

    }
    String get();
}
