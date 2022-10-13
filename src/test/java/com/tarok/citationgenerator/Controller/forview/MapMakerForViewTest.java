package com.tarok.citationgenerator.controller.forview;

import com.tarok.citationgenerator.repository.Book;
import com.tarok.citationgenerator.repository.valueobjects.Publisher;
import com.tarok.citationgenerator.repository.valueobjects.Title;
import com.tarok.citationgenerator.repository.valueobjects.creator.translator.Translators;
import com.tarok.citationgenerator.repository.valueobjects.isbn.IsbnImpl;
import com.tarok.citationgenerator.repository.valueobjects.publishedyear.PublishedYearImpl;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SuppressWarnings("NonAsciiCharacters")
class MapMakerForViewTest {
    @Test
    void makeReferenceStylesAndPutThemOnMapにBookが渡されたとき三つの引用スタイルが乗ったマップが生成される() {
        Book target = new Book();
        target.setAuthors("王陽明");
        target.setTitle(Title.nameOf("伝習録"));
        target.setIsbn(new IsbnImpl("9784121600820"));
        target.setPublisher(new Publisher("中央公論新社"));
        target.setPublishedYear(new PublishedYearImpl("2005.9"));
        target.setTranslators(new Translators("溝口雄三"));
        target.setTranslated(true);

        Map<String, String> actual = MapMakerForView.makeReferenceStylesAndPutThemOnMap(target);

        assertThat(actual.keySet()).isEqualTo(Set.of("APA","standard","SIST02"));
        assertThat(actual.get("APA")).isEqualTo("王陽明(2005)『伝習録』溝口雄三訳、中央公論新社");
        assertThat(actual.get("standard")).isEqualTo("王陽明 『伝習録』 溝口雄三訳、 中央公論新社、 2005年。");
        assertThat(actual.get("SIST02")).isEqualTo("王陽明. 伝習録. 溝口雄三訳. 中央公論新社, 2005.");
    }

    @Test
    void testMakeReferenceStylesAndPutThemOnMapにArticleが渡されたとき三つの引用スタイルが乗ったマップが生成される() {

    }
}