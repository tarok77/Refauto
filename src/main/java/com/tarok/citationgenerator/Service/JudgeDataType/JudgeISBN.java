package com.tarok.citationgenerator.Service.JudgeDataType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 入力されたISBNの判定を行う。
 * ISBNではない、全角のISBN、半角のISBNの三種
 */
public class JudgeISBN {
    public InputtedDataType judge(String inputData) {
        int length = inputData.length();

        if (!(length == 10 || length == 13)) return InputtedDataType.NOT_ISBN;

        if (length == 10) {
            var pattern = Pattern.compile("[０-９]{10}");
            var matcher = pattern.matcher(inputData);
            if(matcher.matches()) return InputtedDataType.TWO_BYTE_ISBN;

            //再代入のほうがましか？
            var pattern2 = Pattern.compile("\\d{10}");
            var matcher2 = pattern2.matcher(inputData);
            if(matcher2.matches()) return InputtedDataType.ONE_BYTE_ISBN;
        }

        var pattern = Pattern.compile("[０-９]{13}");
        Matcher matcher = pattern.matcher(inputData);
        if(matcher.matches()) return InputtedDataType.TWO_BYTE_ISBN;

        var pattern2 = Pattern.compile("\\d{13}");
        var matcher2 = pattern2.matcher(inputData);
        if(matcher2.matches()) return InputtedDataType.ONE_BYTE_ISBN;

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

    //TODO assertionの追加とエラー生成を削除

    /**
     * フォームに入力されたISBNを判定し半角ISBNであることを保証する
     * @param inputtedString　フォームのISBN欄に入力された値
     * @return 半角のISBN
     * @throws IllegalArgumentException isbnではないとJudgeDataTypeが判定した場合に発生
     */
    public String normalizeByteType(String inputtedString) {
        var judge = new JudgeISBN();
        InputtedDataType result = judge.judge(inputtedString);

        //違う場合はTitleとみなす
        if (result == InputtedDataType.NOT_ISBN) {
            throw new IllegalArgumentException("isbnではありません");
        }

        //半角であるときはそのままつかえる
        if (result == InputtedDataType.ONE_BYTE_ISBN) {
            return inputtedString;
        }

        //全角であるときは半角に正規化。
        String normalizedISBN = normalizeTwoByte(inputtedString);
        return normalizedISBN;
    }

}
