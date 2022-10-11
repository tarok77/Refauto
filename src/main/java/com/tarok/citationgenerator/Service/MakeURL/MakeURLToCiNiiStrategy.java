package com.tarok.citationgenerator.Service.MakeURL;
import org.springframework.stereotype.Component;

@Component
public class MakeURLToCiNiiStrategy implements MakeURLStrategy {
    String baseURLFront = "https://cir.nii.ac.jp/opensearch/articles?appid=";
    String ciniiAppId = System.getenv("cinii");
    String baseURLEnd = "&count=30&sortorder=0&format=json";

    @Override
    public String makeURL(With dataType, String searchInfo) {
        if (dataType == With.TITLE) {
            return baseURLFront + ciniiAppId + "&title=" + searchInfo + baseURLEnd;
        }
        return baseURLFront + ciniiAppId + "&creator=" + searchInfo + baseURLEnd;
    }

    @Override
    public String makeURL(With dataType, String title, String creator) {
        return baseURLFront + ciniiAppId + "&title=" + title + "&creator=" + creator + baseURLEnd;
    }
}
