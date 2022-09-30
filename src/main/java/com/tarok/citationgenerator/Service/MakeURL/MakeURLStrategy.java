package com.tarok.citationgenerator.Service.MakeURL;

import org.springframework.stereotype.Component;

@Component
public interface MakeURLStrategy {

    String makeURL(WithWhat dataType, String searchInfo);

    String makeURL(WithWhat dataType, String title, String author);

    static MakeURLStrategy strategyOf(BookOrArticle searched) {
        if (searched == BookOrArticle.BOOK_TYPE) return new MakeURLToKokkaiStrategy();
        return new MakeURLToCiNiiStrategy();
    }
}
