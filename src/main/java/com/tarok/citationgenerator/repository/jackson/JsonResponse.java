package com.tarok.citationgenerator.repository.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tarok.citationgenerator.repository.jackson.Items;
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
