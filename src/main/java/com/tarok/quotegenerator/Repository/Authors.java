package com.tarok.quotegenerator.Repository;

import lombok.Data;
import java.util.List;

@Data
public class Authors {
    private List<Author> authors;

    //作者が二人以上であるとき最初の作者に「...他」をつけて代表させる。
    public String represent() {
        if(authors.size() > 2) {
            return authors.get(0).toString() + "他";
        }

        if(authors.size() > 1) {
            return authors.get(0).toString() + "・" + authors.get(1).toString();
        }

        return authors.get(0).toString();
    }
}
