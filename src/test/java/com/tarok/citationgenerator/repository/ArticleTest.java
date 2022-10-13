package com.tarok.citationgenerator.repository;

import com.tarok.citationgenerator.controller.forview.ArticleForView;
import com.tarok.citationgenerator.repository.valueobjects.creator.author.Authors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class ArticleTest {
    ArticleForView target = new ArticleForView();
    Article sut;

    @BeforeEach
    public void setup() {
        target.setTitle("自分に向けて話すこと、他者に向けて話すこと : ウィトゲンシュタインとフッサール");
        target.setCreators(" 鈴木 崇志");
        target.setPublicationDate("2022-01");
        target.setPublisher("青土社");
        target.setPublicationName(" 青土社");
        target.setPages("223-233");
        target.setVolumeAndNum("49(16)");

        sut = Article.fromView(target);
    }
    @Test
    void fromViewにArticleForViewがわたされたときArticelインスタンスが生成される() {
        Article instance = Article.fromView(target);

        assertThat(instance).isInstanceOf(Article.class);
    }

    @Test
    void getAuthorsで姓名の間のスペースが消える() {
        var actual = sut.getAuthors();

        assertThat(actual).isEqualTo("鈴木崇志");
    }

    @Test
    void getAuthorsReplacedComma() {
        sut.setAuthors(new Authors("作者 一人目, 作者 二人目"));
        var actual = sut.getAuthorsReplacedComma();

        assertThat(actual).isEqualTo("作者一人目・作者二人目");
    }

    @Test
    void convertAPAReferenceによる文献情報の生成() {
        String actual = sut.convertAPAReference();

        assertThat(actual).isEqualTo("鈴木崇志 (2022) 「自分に向けて話すこと、他者に向けて話すこと : ウィトゲンシュタインとフッサール」『 青土社』 49(16), 223-233");
    }

    @Test
    void convertStandardReferenceによる文献情報の生成() {
        String actual = sut.convertStandardReference();

        assertThat(actual).isEqualTo("鈴木崇志 「自分に向けて話すこと、他者に向けて話すこと : ウィトゲンシュタインとフッサール」 『 青土社』 49巻16号、2022年、223-233頁。");
    }

    @Test
    void convertSIST02Referenceによる文献情報の生成() {
        String actual = sut.convertSIST02Reference();

        assertThat(actual).isEqualTo("鈴木崇志. 自分に向けて話すこと、他者に向けて話すこと : ウィトゲンシュタインとフッサール.  青土社. 2022, 49(16), p.223-233.");
    }
}