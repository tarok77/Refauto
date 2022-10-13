package com.tarok.citationgenerator.repository.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * ciniiapiをのjsonに対応するための中身となるクラス。JsonResponseに保有される。
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString
public class Items {
    public String title;
    @JsonProperty("dc:creator")
    public List<String> creator;
    @JsonProperty("dc:publisher")
    public String publisher;
    @JsonProperty("prism:publicationName")
    public String publicationName;
    @JsonProperty("prism:volume")
    public String volume;
    @JsonProperty("prism:number")
    public String number;
    @JsonProperty("prism:startingPage")
    public String startingPage;
    @JsonProperty("prism:endingPage")
    public String endingPage;
    @JsonProperty("prism:publicationDate")
    public String publicationDate;

    public Optional<List<String>> getCreator() {
        return Optional.ofNullable(creator);
    }

    /**
     * フィールドのList<String> をコンマ区切りのストリングにして返すメソッド。
     * @return コンマ区切りのcreatorフィールド nullの時は空文字を返す。
     */
    public String getCreatorString() {
        StringBuilder builder = new StringBuilder();
        List<String> creatorList = getCreator().orElse(new ArrayList<>());

        for(String creator: creatorList) {
            builder.append(creator);
            builder.append(", ");
        }
        //末尾の", "を削除して戻り値に
        if(builder.isEmpty()) return "";
        return builder.delete(builder.length()-2,builder.length()).toString();
    }
}
