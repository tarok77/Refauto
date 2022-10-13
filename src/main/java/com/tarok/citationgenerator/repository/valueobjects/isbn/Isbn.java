package com.tarok.citationgenerator.repository.valueobjects.isbn;

public interface Isbn {

    String getIsbn();

    static Isbn numberOf(String number) {
        if(number.isBlank())return new NullIsbn();
        return IsbnImpl.numberOf(number);
    }
    static  Isbn numberOf(long number) {
        return IsbnImpl.numberOf(number);
    }
}
