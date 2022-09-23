package com.tarok.citationgenerator.Controller.Form;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
/**
 * タイトルもしくは作者が入力されていることを確認するためのヴァリデーションのためのフォームクラス
 */

@Data
public class TitleAndAuthor {

    String title;

    String author;

    @AssertTrue(message = "どちらかは入力してください。")
    public boolean isFilledEitherOne() {
        return (!(title.isBlank()&&author.isBlank()));
    }
}
