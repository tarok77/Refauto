package com.tarok.citationgenerator.Repository;

import com.tarok.citationgenerator.Controller.ArticleForView;
import com.tarok.citationgenerator.Repository.ValueObjects.PublicationName;
import com.tarok.citationgenerator.Repository.ValueObjects.Publisher;
import com.tarok.citationgenerator.Repository.ValueObjects.Title;
import com.tarok.citationgenerator.Repository.ValueObjects.VolumeAndNum;
import com.tarok.citationgenerator.Repository.ValueObjects.creator.author.Authors;
import com.tarok.citationgenerator.Repository.ValueObjects.publishedyear.PublishedYear;
import lombok.Data;

import java.util.List;

@Data
public class Article {
    private Authors authors;

    private PublishedYear publishedYear;

    private Title title;

    private PublicationName publicationName;

    private VolumeAndNum volumeAndNum;

    private String pages;

    private Publisher publisher;

    //雑誌掲載論文と単行本収録の論文で提示する情報が違うため 単行本収録の論文がそもそも元データにないため不要かも
    private boolean isPartOfBook = false;

    //TODO 翻訳論文の時著者名がタイトルの前に加わっているときとクリエーターに訳者と併記されている場合の二パターンがある　どうするか

    public static Article fromView(ArticleForView forView) {
        var article = new Article();
        article.setPublishedYear(PublishedYear.of(forView.getPublicationDate()));
        article.setTitle(Title.nameOf(forView.getTitle()));
        article.setPublicationName(PublicationName.of(forView.getPublicationName()));
        article.setVolumeAndNum(VolumeAndNum.of(forView.getVolumeAndNum()));
        article.setPages(forView.getPages());
        article.setPublisher(Publisher.nameOf(forView.getPublisher()));

        article.setAuthors(new Authors(List.of(forView.getCreators())));

        return article;
    }

    public String getAuthors() {
        return authors.getAuthorsNames().replaceAll(" ", "");
    }

    public String getAuthorsReplacedComma() {
        return getAuthors().replaceAll(",", "・");
    }

    public String convertAPAReference() {
        return getAuthorsReplacedComma() + " (" + publishedYear.get() + ") " +
                title.getWithBrackets() + publicationName.getWithDoubleBrackets() + " "
                + volumeAndNum.get() + ", " + pages;
    }

    public String convertStandardReference() {
        return getAuthorsReplacedComma() + " " + title.getWithBrackets() +  " " + publicationName.getWithDoubleBrackets() + " "
                + volumeAndNum.getVolumeAndNumInJapanese() + "、" +  publishedYear.get() + "年、" + pages + "頁。";
    }

    public String convertSIST02Reference() {
        return getAuthors() + ". " + title.get() + ". " + publicationName.get() + ". " + publishedYear.get() + ", "
                + volumeAndNum.get() + ", p." + pages + ".";
    }
}