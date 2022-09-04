package com.tarok.quotegenerator.Service.JudgeDataType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgeDataType {
    //TODO 十桁　ISBNの最終桁がXであるときisbnではないと判断してしまう　要修正　ユーザーからの入力しか判断しないため影響は限定的
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
}
