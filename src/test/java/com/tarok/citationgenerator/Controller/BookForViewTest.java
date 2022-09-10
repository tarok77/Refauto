package com.tarok.citationgenerator.Controller;

import com.tarok.citationgenerator.Repository.RawBook;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class BookForViewTest {

    @Test
    void forViewによるrawBookStringリストのStringへの変更() {
        var target = new RawBook();
        target.setIsbn("11111");target.setCreatorList(List.of("test","test2"));target.setPublisher("publisher");
        target.setTitle("testbook");target.setPublishedYear("1901");
        var result = BookForView.toView(target);
        assertThat(result.getCreators()).isEqualTo("test test2");
    }
}