package com.tarok.quotegenerator.Repository.ValueObjects;
//共同訳、監訳などを扱うことを考えると案外難しい。nullの問題もある。
public class Translator {
    private final String translator;

    public Translator(String translator) {
        this.translator = translator;
    }
}
