package com.tarok.citationgenerator.repository.valueobjects.creator.translator;

import lombok.ToString;

//共同訳、監訳などを扱うことを考えると案外難しい。nullの問題もある。
@ToString
public class Translator {
    private final String translator;

    private Translator(String name) {
        this.translator = name;
    }

    public String getTranslatorName() {
        return this.translator;
    }

    public static Translator nameOf(String translator) {
        return new Translator(translator);
    }
}
