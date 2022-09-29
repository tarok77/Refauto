package com.tarok.citationgenerator.Repository.ValueObjects.creator.translator;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Setter
public class Translators {
    List<Translator> translators ;

    public Translators(String translator) {
        this.translators = List.of(Translator.nameOf(translator));
    }

    public Translators(List<String> translators) {
        this.translators = translators.stream().map(Translator::nameOf).toList();
    }


    public boolean isEmpty() {
        if (translators.isEmpty()) return true;
        return false;
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
            //TODO builderに変えるかどうか要検討
            result += "、" + translator.getTranslatorName();
        }
        result += "訳";
        //監訳の場合のスペースを排除
        return result.replaceAll(" 監|　監", "監");
    }

    public String getTranslatorsNamesWithComma() {
        String names = getTranslatorsNames();
        if(names.isEmpty()) return "";
        return names + "、";
    }
}