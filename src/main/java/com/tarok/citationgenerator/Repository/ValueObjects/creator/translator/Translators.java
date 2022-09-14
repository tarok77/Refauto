package com.tarok.citationgenerator.Repository.ValueObjects.creator.translator;

import java.util.List;

public class Translators {
    List<Translator> translators;

    public Translators(String translator) {
        this.translators = List.of(Translator.nameOf(translator));
    }

    public Translators(List<String> translators) {
        this.translators = translators.stream().map(Translator::nameOf).toList();
    }


    public boolean isEmpty() {
        if (translators.isEmpty()) return false;
        return true;
    }

    public String getTranslatorsNames() {
        //translatorsが空のとき翻訳書ではないとみなしなにも書き出さないようにする
        if(translators.isEmpty()) return "";

        String result = null;
        for (var translator : translators) {
            if (result == null) {
                result = translator.getTranslatorName();
                continue;
            }
            result += "、" + translator.getTranslatorName();
        }
        //訳者がいるときだけ読点で区切られるためにここでつけておく
        result += "訳、";
        return result;
    }
}