package com.tarok.citationgenerator.controller.forview;
import com.tarok.citationgenerator.repository.jackson.Items;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Data
@Slf4j
@ToString
public class ArticleForView {
    private String title;

    private String creators;

    private String publicationName;

    private String publicationDate;

    private String publisher;

    private String pages;

    private String volumeAndNum;

    /**
     * jsonの戻り値からview用の論文データを作るためのスタティックファクトリメソッド
     *
     * @param response CiNiiapiからの戻り値の論文データ
     * @return viewに送るために最低限の整形をほどこした論文のデータ
     */
    public static ArticleForView of(Items response) {
        var article = new ArticleForView();

        article.setTitle(response.getTitle());
        article.setPublicationName(response.getPublicationName());
        article.setPublicationDate(response.getPublicationDate());
        article.setPublisher(response.getPublisher());
        article.setCreators(response.getCreatorString());

        if (response.getEndingPage() == null) {
            article.setPages(response.getStartingPage());
        } else {
            article.setPages(response.getStartingPage() + "-" + response.getEndingPage());
        }

        //volumeとnumに含まれるデータが多岐にわたるため処理が複雑になる 以降はその対策
        String volume;
        String num;
        volume = response.getVolume();
        num = response.getNumber();

        if (Objects.isNull(volume) && Objects.isNull(num)) {
            article.setVolumeAndNum("NO_DATA");
            return article;
        }
        if (Objects.isNull(num)) {
            article.setVolumeAndNum(volume);
            return article;
        }
        //本来volumeに入れるべき値をnumに入れられたときの対策
        if (Objects.isNull(volume)) {
            article.setVolumeAndNum(num);
            return article;
        }
        //例　３巻５号は3(5)
        article.setVolumeAndNum(volume + "(" + num + ")");
        return article;
    }
}
