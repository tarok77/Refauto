package com.tarok.quotegenerator.Service;

import com.tarok.quotegenerator.Service.JudgeDataType.InputtedDataType;
import com.tarok.quotegenerator.Service.JudgeDataType.JudgeDataType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class JudgeIsbnTest {

    JudgeDataType judge = new JudgeDataType();

    @Test
    public void 十文字の半角数字にONEBYTEISBNを返す() {
        InputtedDataType result = judge.judge("1234567890");
        assertThat(result).isEqualTo(InputtedDataType.ONE_BYTE_ISBN);
    }

    @Test
    public void 十文字の全角数字にTWOBYTEISBNを返す() {
        InputtedDataType result2 = judge.judge("１２３４５６７８９０");
        assertThat(result2).isEqualTo(InputtedDataType.TWO_BYTE_ISBN);
    }

    @Test
    public void 十二文字の数字にNOT_ISBNを返す() {
        InputtedDataType result = judge.judge("978412160082");
        assertThat(result).isEqualTo(InputtedDataType.NOT_ISBN);
        InputtedDataType result2 = judge.judge("１２３４５６７８９０１２");
        assertThat(result2).isEqualTo(InputtedDataType.NOT_ISBN);
    }

    @Test
    public void 全角の数字１３文字にTWO_BYTE_ISBNを返す() {
        InputtedDataType result2 = judge.judge("１２３４５６７８９０１２３");
        assertThat(result2).isEqualTo(InputtedDataType.TWO_BYTE_ISBN);
    }

    @Test
    public void 十文字の正規表現に想定されない三文字が付く時NOT_ISBNを返す() {
        InputtedDataType result = judge.judge("1234567890あああ");
        assertThat(result).isEqualTo(InputtedDataType.NOT_ISBN);
        InputtedDataType result2 = judge.judge("１２３４５６７８９０aaa");
        assertThat(result2).isEqualTo(InputtedDataType.NOT_ISBN);
    }


}