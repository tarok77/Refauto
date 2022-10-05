package com.tarok.citationgenerator.Repository.Jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

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


}
