package com.tarok.citationgenerator.Repository;

import java.util.ArrayList;
import java.util.List;

class CreatorPair {
    List<String> authors = new ArrayList<>();
    List<String> translators = new ArrayList<>();

    public void addAuthor(String author) {
        authors.add(author);
    }

    public void addTranslator(String translator) {
        translators.add(translator);
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<String> getTranslators() {
        return translators;
    }
}
