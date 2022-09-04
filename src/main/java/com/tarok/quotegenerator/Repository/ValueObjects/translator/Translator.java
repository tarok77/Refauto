package com.tarok.quotegenerator.Repository.ValueObjects.translator;

//共同訳、監訳などを扱うことを考えると案外難しい。nullの問題もある。
public class Translator {
    private final String translator;

    private Translator(String name) {
        this.translator = name;
    }

    @Override
    public String toString() {
        return translator;
    }

    public static Translator nameOf(String translator) {
        return new Translator(translator);
    }
}
