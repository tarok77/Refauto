package com.tarok.citationgenerator.Repository.Jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * ciniiapiのresponseであるjsonに対応するためクラス。Itemsのリストを保有する。
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class JsonResponse {
    public List<Items> items;
}
