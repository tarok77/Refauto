package com.tarok.citationgenerator.Controller;

import com.tarok.citationgenerator.Repository.Jackson.Items;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ArticleForView {
    private String title;

    private String creators;

    private String publicationName;

    private String publicationDate;

    private String publisher;

    private String pages;

    private int volume;

    public static ArticleForView of(Items response) {
        var article = new ArticleForView();

        article.setTitle(response.getTitle());
        article.setCreators(response.getCreator().stream().reduce(" ",(a, b) -> a + "," + b).replaceFirst(",","").trim());
        article.setPublicationName(response.getPublicationName());
        article.setPublicationDate(response.getPublicationDate());
        article.setPublisher(response.getPublisher());
        article.setPages(response.getStartingPage() + "-" + response.getEndingPage());
        article.setVolume(response.getVolume());

        return article;
    }
}
