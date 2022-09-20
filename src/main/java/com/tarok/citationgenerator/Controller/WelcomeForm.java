package com.tarok.citationgenerator.Controller;

import lombok.Data;

/**
 * ユーザーに検索条件を入力してもらうフォームに対応するクラス。
 * おもにValidationのために使用
 */
@Data
public class WelcomeForm {
    String title;

    String author;

    String isbn;
}
