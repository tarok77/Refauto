package com.tarok.quotegenerator.Repository.ValueObjects.isbn;

import lombok.Data;

@Data
public class IsbnImpl implements Isbn{
    //エンコードの関係でStringじゃないとだめかも
    // TODO 10桁のisbnには最終桁にチェックデジットのX（１０の意味) が含まれる 以下wikiより
//    （チェックディジットを除いた左側の桁から10、9、8…2を掛けてそれらの和を取る。和を11で割って出た余りを11から引く）
//    （チェックディジットを除いた一番左側の桁から順に1、3、1、3…を掛けてそれらの和を取る。
//    和を10で割って出た余りを10から引く。ただし、10で割って出た余りの下1桁が0の場合はチェック数字を0とする。）

    private final long isbnExceptLast;
    private char checkDigit;

    //    引数にXが含まれる可能性があるためStringにしないとまずい
    public IsbnImpl(long number) throws IllegalArgumentException {
        judgeArgument(number);
        this.isbnExceptLast = number / 10;
        int lastNum = (int) (number % 10);
        this.checkDigit = (char) (lastNum + '0');
    }

    public IsbnImpl(String stringNumber) throws IllegalArgumentException {
        String rest = clipCheckDigit(stringNumber);
        long number = Long.parseLong(rest);
        judgeNumberFromString(number);
        this.isbnExceptLast = number;
    }

    public String getIsbn() {
        return String.valueOf(isbnExceptLast) + checkDigit;
    }

    public static Isbn numberOf(String number) {
        try {
            return new IsbnImpl(number);
        } catch (IllegalArgumentException e) {
            return new SuspiciousIsbn(number);
        }
    }
    public static Isbn numberOf(long number) {
        try {
            return new IsbnImpl(number);
        } catch (IllegalArgumentException e) {
            return new SuspiciousIsbn(number);
        }
    }
    private void judgeArgument(long number) throws IllegalArgumentException {
        if (number <= 0) throw new IllegalArgumentException("ISBNの値が不正です");

        int numberOfDigits = (int) Math.log10(number) + 1;
        if (numberOfDigits != 10 && numberOfDigits != 13) {
            throw new IllegalArgumentException("ISBNの値が不正です");
        }
    }

    private void judgeNumberFromString(long number) throws IllegalArgumentException {
        if (number <= 0) throw new IllegalArgumentException("ISBNの値が不正です");

        int numberOfDigits = (int) Math.log10(number) + 1;
        if (numberOfDigits != 9 && numberOfDigits != 12) {
            throw new IllegalArgumentException("ISBNの値が不正です");
        }
    }

    private String clipCheckDigit(String numberString) {
        int length = numberString.length();
        this.checkDigit = numberString.charAt(length - 1);
        return numberString.substring(0, length - 1);
    }

}
