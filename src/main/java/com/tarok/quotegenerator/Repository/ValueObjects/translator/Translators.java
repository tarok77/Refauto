package com.tarok.quotegenerator.Repository.ValueObjects.translator;

import java.util.ArrayList;
import java.util.List;

public class Translators {
    List<Translator> translators;

    public void setTranslators(String translator) {
        this.translators = List.of(Translator.nameOf(translator));
    }
}