package com.tarok.citationgenerator.Service.JudgeDataType;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 入力されたISBNの判定を行う。
 * ISBNではない、全角のISBN、半角のISBNの三種
 */
@Slf4j
public class JudgeISBN {
    public InputtedDataType judge(String inputData) {
        int length = inputData.length();

        if (!(length == 10 || length == 13)) return InputtedDataType.NOT_ISBN;

        if (length == 10) {
            var patternTwoByte = Pattern.compile("[０-９]{10}");
            var matcherTwoByte = patternTwoByte.matcher(inputData);
            if(matcherTwoByte.matches()) return InputtedDataType.TWO_BYTE_ISBN;

            var patternOneByte = Pattern.compile("\\d{10}");
            var matcherOneByte = patternOneByte.matcher(inputData);
            if(matcherOneByte.matches()) return InputtedDataType.ONE_BYTE_ISBN;
        }

        var patternTwoByte = Pattern.compile("[０-９]{13}");
        Matcher matcherTwoByte = patternTwoByte.matcher(inputData);
        if(matcherTwoByte.matches()) return InputtedDataType.TWO_BYTE_ISBN;

        var patternOneByte = Pattern.compile("\\d{13}");
        var matcherOneByte = patternOneByte.matcher(inputData);
        if(matcherOneByte.matches()) return InputtedDataType.ONE_BYTE_ISBN;

        return InputtedDataType.NOT_ISBN;
    }
    /**
     * 全角のISBNを半角に変更する
     * @param twoByteNumber 全角のISBN
     * @return 半角のISBN
     */
    public String normalizeTwoByte(String twoByteNumber) {
        var builder = new StringBuilder();
        for (int i = 0; i < twoByteNumber.length(); i++) {
            char c = twoByteNumber.charAt(i);
            if (c < '０' || c > '９') {
                throw new IllegalArgumentException("入力値が想定されていない値です。");
            }
            int c2 = c - '０';
            builder.append(c2);
        }
        return builder.toString();
    }

    /**
     * フォームに入力されたISBNを判定し半角ISBNであることを保証する
     * @param inputtedString　フォームのISBN欄に入力された値
     * @return 半角のISBN
     * @throws IllegalArgumentException 入力がisbnではない場合に発生
     */
    public String normalizeByteType(String inputtedString) {
        var judge = new JudgeISBN();
        InputtedDataType result = judge.judge(inputtedString);

        //フォームのValidationではじかれるはずなのでエラーを出力する
        if (result == InputtedDataType.NOT_ISBN) {
            log.warn("ISBNに不正な入力値");
            throw new IllegalArgumentException("isbnではありません");
        }

        //半角であるときはそのままつかえる
        if (result == InputtedDataType.ONE_BYTE_ISBN) {
            return inputtedString;
        }

        //全角であるときは半角に正規化。
        return normalizeTwoByte(inputtedString);
    }

}
