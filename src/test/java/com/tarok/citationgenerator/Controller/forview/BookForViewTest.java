package com.tarok.citationgenerator.controller.forview;

import com.tarok.citationgenerator.repository.RawBook;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class BookForViewTest {

    @Test
    void ConvertToViewにrawBookインスタンスが渡されたときBookForViewのStringフィールドに変更される() {
        //Arrange
        var target = new RawBook();
        target.setIsbn("9784309256283");target.setCreatorList(List.of("エルンスト・H・ゴンブリッチ 著","天野衛", "大西広","奥野皐", "桐山宣雄", "長谷川摂子", "長谷川宏", "林道郎", "宮腰直人 訳"));target.setPublisher("河出書房新社");
        target.setTitle("美術の物語");target.setPublishedYearAndMonth("2019");
        //Act
        var instance = BookForView.ConvertForView(target);
        //Assert
        assertThat(instance.getCreators()).isEqualTo("エルンスト・H・ゴンブリッチ 著,天野衛,大西広,奥野皐,桐山宣雄,長谷川摂子,長谷川宏,林道郎,宮腰直人 訳");
        assertThat(instance.getPublishedYear()).isEqualTo("2019");
    }

}