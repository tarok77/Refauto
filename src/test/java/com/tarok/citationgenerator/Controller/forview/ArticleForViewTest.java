package com.tarok.citationgenerator.controller.forview;
import com.tarok.citationgenerator.repository.jackson.Items;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class ArticleForViewTest {
    Items target = new Items();

    @BeforeEach
    void setUp() {
        target.setTitle("title");
        target.setCreator(List.of("creator", "translator"));
        target.setPublisher("publisher");
        target.setPublicationName("zasshi");
        target.setVolume("2");
        target.setNumber("3");
        target.setStartingPage("1");
        target.setEndingPage("100");
        target.setPublicationDate("1993");
    }

    @Test
    void スタティックファクトリメソッドofでのインスタンスの生成() {
        //Arrange
        //Act
        var instance = ArticleForView.of(target);

        //Assert
        assertThat(instance.getPages()).isEqualTo("1-100");
        assertThat(instance.getTitle()).isEqualTo("title");
        assertThat(instance.getCreators()).isEqualTo("creator, translator");
        assertThat(instance.getPublisher()).isEqualTo("publisher");
        assertThat(instance.getPublicationName()).isEqualTo("zasshi");
        assertThat(instance.getVolumeAndNum()).isEqualTo("2(3)");
        assertThat(instance.getPublicationDate()).isEqualTo("1993");
    }

    @Test
    void targetにnullを含むオブジェクトを渡されてもNPEを引き起こさない() {
        //Arrange
        target.setTitle(null); target.setStartingPage(null); target.setEndingPage(null); target.setPublisher(null); target.setNumber(null); target.setVolume(null);
        target.setPublicationDate(null); target.setPublicationName(null);target.setCreator(null);

        //Act
        var instance = ArticleForView.of(target);
    }

    @Test
    void EndingPageがnullのときArticleFOrViewのページ数がハイフンを含まないStartingPageだけになる() {
        //Arrange
        target.setEndingPage(null);
        //Act
        var instance = ArticleForView.of(target);
        var actual = instance.getPages();
        //Assert
        assertThat(actual).isEqualTo("1");
    }
}