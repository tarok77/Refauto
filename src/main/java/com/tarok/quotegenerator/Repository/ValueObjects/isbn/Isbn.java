package com.tarok.quotegenerator.Repository.ValueObjects.isbn;

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
