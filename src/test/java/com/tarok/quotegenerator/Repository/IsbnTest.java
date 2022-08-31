package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.Isbn;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IsbnTest {
    @Test
    public void Isbnが１０桁もしくは1１３桁である場合以外エラーがでる() throws Exception {
        assertThatThrownBy(() -> new Isbn(-1L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
        assertThatThrownBy(() -> new Isbn(1L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
        assertThatThrownBy(() -> new Isbn(10000000000L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
        assertThatThrownBy(() -> new Isbn(10000000000000000L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
    }

    @Test
    public void Isbnが１０桁もしくは１３桁でオブジェクトが生成される() {
        assertThat(new Isbn(1000000000L).getIsbn()).isEqualTo(1000000000L);
        assertThat((new Isbn(1000000000000L)).getIsbn()).isEqualTo((1000000000000L));
    }

}
