package com.tarok.citationgenerator.service.makeurl;

import org.springframework.stereotype.Component;

@Component
public interface MakeURLStrategy {

    String makeURL(With dataType, String searchInfo);

    String makeURL(With dataType, String title, String author);

    static MakeURLStrategy of(BookOrArticle searched) {
        if (searched == BookOrArticle.BOOK) return new MakeURLToKokkaiStrategy();
        return new MakeURLToCiNiiStrategy();
    }
}
