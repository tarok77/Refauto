package com.tarok.citationgenerator.Repository;

import com.tarok.citationgenerator.Repository.ValueObjects.isbn.IsbnImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
public class IsbnImplTest {
    @Test
    public void Isbnが１０桁もしくは１３桁である場合以外エラーがでる() {
        assertThatThrownBy(() -> new IsbnImpl(-1L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
        assertThatThrownBy(() -> new IsbnImpl(1L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
        assertThatThrownBy(() -> new IsbnImpl(10000000000L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
        assertThatThrownBy(() -> new IsbnImpl(10000000000000000L)).isInstanceOf(IllegalArgumentException.class).hasMessage("ISBNの値が不正です");
    }

    @Test
    public void Isbnが１０桁もしくは１３桁でオブジェクトが生成される() {
        assertThat(new IsbnImpl(1000000000L).getIsbnExceptLast()).isEqualTo(100000000L);
        assertThat((new IsbnImpl(1000000000000L)).getIsbnExceptLast()).isEqualTo((100000000000L));
    }

    @Test
    public void Stringを引数にするコンストラクタのフィールド値の正当性() {
        IsbnImpl isbn = new IsbnImpl("9784121600820");
        assertThat(isbn.getIsbnExceptLast()).isEqualTo(978412160082L);
        assertThat(isbn.getCheckDigit()).isEqualTo('0');

        isbn = new IsbnImpl("123456789X");
        assertThat(isbn.getIsbnExceptLast()).isEqualTo(123456789L);
        assertThat(isbn.getCheckDigit()).isEqualTo('X');
    }

    @Test
    public void getISBNがフィールドの値を結合して返すこと() {
        IsbnImpl sut = new IsbnImpl(1234567890);
        assertThat(sut.getIsbnExceptLast()).isEqualTo(123456789);
        assertThat(sut.getCheckDigit()).isEqualTo('0');
        assertThat(sut.getIsbn()).isEqualTo("1234567890");
    }

}


