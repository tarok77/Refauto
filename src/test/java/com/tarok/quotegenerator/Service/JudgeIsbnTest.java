package com.tarok.quotegenerator.Service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class JudgeIsbnTest {

    JudgeIsbn judge = new JudgeIsbn();

    @Test
    public void 十文字の半角数字にONEBYTEISBNを返す() {
        ISBNOrNotISBN result = judge.judge("1234567890");
        assertThat(result).isEqualTo(ISBNOrNotISBN.ONEBYTEISBN);
    }

    @Test
    public void 十文字の全角数字にTWOBYTEISBNを返す() {
        ISBNOrNotISBN result2 = judge.judge("１２３４５６７８９０");
        assertThat(result2).isEqualTo(ISBNOrNotISBN.TWOBYTEISBN);
    }

    @Test
    public void 十二文字の数字にNOTISBNを返す() {
        ISBNOrNotISBN result = judge.judge("978412160082");
        assertThat(result).isEqualTo(ISBNOrNotISBN.NOTISBN);
        ISBNOrNotISBN result2 = judge.judge("１２３４５６７８９０１２");
        assertThat(result2).isEqualTo(ISBNOrNotISBN.NOTISBN);
    }

    @Test
    public void 全角の数字１３文字にTWOBYTEISBNを返す() {
        ISBNOrNotISBN result2 = judge.judge("１２３４５６７８９０１２３");
        assertThat(result2).isEqualTo(ISBNOrNotISBN.TWOBYTEISBN);
    }

    @Test
    public void 十文字の正規表現に想定されない三文字が付く時NOTISBNを返す() {
        ISBNOrNotISBN result = judge.judge("1234567890あああ");
        assertThat(result).isEqualTo(ISBNOrNotISBN.NOTISBN);
        ISBNOrNotISBN result2 = judge.judge("１２３４５６７８９０aaa");
        assertThat(result2).isEqualTo(ISBNOrNotISBN.NOTISBN);
    }


}