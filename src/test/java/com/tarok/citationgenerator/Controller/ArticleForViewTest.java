package com.tarok.citationgenerator.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tarok.citationgenerator.Repository.Jackson.Items;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("NonAsciiCharacters")
class ArticleForViewTest {

    @Test
    void スタティックファクトリメソッドofでのインスタンスの生成() {
        //Arrange
        var target = new Items();
        target.setTitle("title");
        target.setCreator(List.of("creator","translator"));
        target.setPublisher("publisher");
        target.setPublicationName("zasshi");
        target.setVolume(2);
        target.setStartingPage("1");
        target.setEndingPage("100");
        target.setPublicationDate("1993");

        //Act
        var instance = ArticleForView.of(target);

        //Assert
        assertThat(instance.getPages()).isEqualTo("1-100");
        assertThat(instance.getTitle()).isEqualTo("title");
        assertThat(instance.getCreators()).isEqualTo("creator,translator");
        assertThat(instance.getPublisher()).isEqualTo("publisher");
        assertThat(instance.getPublicationName()).isEqualTo("zasshi");
        assertThat(instance.getVolume()).isEqualTo(2);
        assertThat(instance.getPublicationDate()).isEqualTo("1993");
    }
}