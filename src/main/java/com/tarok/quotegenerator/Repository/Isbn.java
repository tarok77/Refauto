package com.tarok.quotegenerator.Repository;

import lombok.Getter;

public class Isbn {
    //エンコードの関係でStringじゃないとだめかも
    @Getter
    private final long isbn;

    Isbn(long number) throws IllegalArgumentException {
        if(number <= 0){throw new IllegalArgumentException("ISBNの値が不正です");}

        int numberOfDigits = (int)Math.log10(number) + 1;
        if(numberOfDigits != 10 && numberOfDigits != 13) {
            throw new IllegalArgumentException("ISBNの値が不正です");
        }

        this.isbn = number;
    }
}
