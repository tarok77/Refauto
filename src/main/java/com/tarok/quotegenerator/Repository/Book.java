package com.tarok.quotegenerator.Repository;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Book {
    private String title;

    private Authors authors;

    //Json戻り値の型にあわせてvalue objectに変えたほうがいいかも。
    private String publishedYear;

    private String publisher;

    //情報の取得法によってはnullになってしまう可能性がある。entityのIDにはできないかな。
    private Isbn isbn;

    private String translator;

}
