package com.tarok.quotegenerator.Repository;

import lombok.Data;

@Data
public class bookEntity {
    private String title;

    private String author;

    private String translator;

    //Json戻り値の型にあわせてvalue objectに変えたほうがいいかも。
    private String publishedDate;

    private String publisher;

    //情報の取得法によってはnullになってしまう可能性がある。entityのIDにはできないかな。
    private Isbn isbn;
}
