package com.tarok.citationgenerator.Repository;

import com.tarok.citationgenerator.Repository.ValueObjects.Publisher;
import com.tarok.citationgenerator.Repository.ValueObjects.Title;
import com.tarok.citationgenerator.Repository.ValueObjects.creator.author.Authors;
import com.tarok.citationgenerator.Repository.ValueObjects.publishedyear.PublishedYear;
import lombok.Data;

@Data
public class Article {
    private Authors authors;

    private PublishedYear publishedYear;

    private Title title;

    private String publicationName;

    private int volume;

    private String pages;

    private Publisher publisher;

    //雑誌掲載論文と単行本収録の論文で提示する情報が違うため
    private boolean isPartOfBook;
}
