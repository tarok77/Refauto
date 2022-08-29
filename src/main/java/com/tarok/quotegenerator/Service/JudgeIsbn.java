package com.tarok.quotegenerator.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgeIsbn {
    public ISBNOrNotISBN judge(String inputData) {
        int length = inputData.length();

        if (!(length == 10 || length == 13)) return ISBNOrNotISBN.NOTISBN;

        if (length == 10) {
            var pattern = Pattern.compile("[０-９]{10}");
            var matcher = pattern.matcher(inputData);
            if(matcher.matches()) return ISBNOrNotISBN.TWOBYTEISBN;

            //再代入のほうがましか？
            var pattern2 = Pattern.compile("\\d{10}");
            var matcher2 = pattern2.matcher(inputData);
            if(matcher2.matches()) return ISBNOrNotISBN.ONEBYTEISBN;
        }

        var pattern = Pattern.compile("[０-９]{13}");
        Matcher matcher = pattern.matcher(inputData);
        if(matcher.matches()) return ISBNOrNotISBN.TWOBYTEISBN;

        var pattern2 = Pattern.compile("\\d{13}");
        var matcher2 = pattern2.matcher(inputData);
        if(matcher2.matches()) return ISBNOrNotISBN.ONEBYTEISBN;

        return ISBNOrNotISBN.NOTISBN;
    }
}
