package com.tarok.citationgenerator.Repository.ValueObjects.creator.translator;

import com.tarok.citationgenerator.Repository.ExistDataType;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@ToString
public class Translators {
    List<Translator> translators;
    ExistDataType dataType;

    public Translators(String translator) {
        this.translators = List.of(Translator.nameOf(translator));
        this.dataType = ExistDataType.SUSPICIOUS;
    }

    public Translators(List<String> translators) {
        this.translators = translators.stream().map(Translator::nameOf).toList();
        this.dataType = ExistDataType.SUSPICIOUS;
    }

    private Translators(ExistDataType dataType) {
        if(!dataType.equals(ExistDataType.NOT_EXIST)) throw new IllegalArgumentException("この操作は認められていません。");

        //npe対策で空のリストを持たせる
        this.translators = new ArrayList<>();
        this.dataType = ExistDataType.NOT_EXIST;
    }

    public static Translators notExist() {
        return new Translators(ExistDataType.NOT_EXIST);
    }

    public ExistDataType arePresent() {
        return this.dataType;
    }
    public List<String> getTranslatorNames() {
        return translators.stream().map(Translator::getTranslatorName).toList();
    }
}