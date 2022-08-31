package com.tarok.quotegenerator.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgeIsbn {
    public ISBNOrNot judge(String inputData) {
        int length = inputData.length();

        if (!(length == 10 || length == 13)) return ISBNOrNot.NOT_ISBN;

        if (length == 10) {
            var pattern = Pattern.compile("[０-９]{10}");
            var matcher = pattern.matcher(inputData);
            if(matcher.matches()) return ISBNOrNot.TWO_BYTE_ISBN;

            //再代入のほうがましか？
            var pattern2 = Pattern.compile("\\d{10}");
            var matcher2 = pattern2.matcher(inputData);
            if(matcher2.matches()) return ISBNOrNot.ONE_BYTE_ISBN;
        }

        var pattern = Pattern.compile("[０-９]{13}");
        Matcher matcher = pattern.matcher(inputData);
        if(matcher.matches()) return ISBNOrNot.TWO_BYTE_ISBN;

        var pattern2 = Pattern.compile("\\d{13}");
        var matcher2 = pattern2.matcher(inputData);
        if(matcher2.matches()) return ISBNOrNot.ONE_BYTE_ISBN;

        return ISBNOrNot.NOT_ISBN;
    }
}
