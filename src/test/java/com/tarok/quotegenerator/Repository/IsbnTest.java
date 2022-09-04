package com.tarok.quotegenerator.Repository;

import com.tarok.quotegenerator.Repository.ValueObjects.Isbn;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
public class IsbnTest {
    @Test
    public void Isbnが１０桁もしくは1１３桁である場合以外エラーがでる() {
        assertThatThrownBy(() -> new Isbn(-1L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
        assertThatThrownBy(() -> new Isbn(1L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
        assertThatThrownBy(() -> new Isbn(10000000000L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
        assertThatThrownBy(() -> new Isbn(10000000000000000L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
    }

    @Test
    public void Isbnが１０桁もしくは１３桁でオブジェクトが生成される() {
        assertThat(new Isbn(1000000000L).getIsbnExceptLast()).isEqualTo(100000000L);
        assertThat((new Isbn(1000000000000L)).getIsbnExceptLast()).isEqualTo((100000000000L));
    }

    @Test
    public void Stringを引数にするコンストラクタのフィールド値の正当性() {
        Isbn isbn = new Isbn("9784121600820");
        assertThat(isbn.getIsbnExceptLast()).isEqualTo(978412160082L);
        assertThat(isbn.getCheckDigit()).isEqualTo('0');

        isbn = new Isbn("123456789X");
        assertThat(isbn.getIsbnExceptLast()).isEqualTo(123456789L);
        assertThat(isbn.getCheckDigit()).isEqualTo('X');
    }

    @Test
    public void getISBNがフィールドの値を結合して返すこと() {
        Isbn sut = new Isbn(1234567890);
        assertThat(sut.getIsbnExceptLast()).isEqualTo(123456789);
        assertThat(sut.getCheckDigit()).isEqualTo('0');
        assertThat(sut.getIsbn()).isEqualTo("1234567890");
    }

}


