package com.tarok.citationgenerator.Repository.ValueObjects;

import lombok.ToString;

@ToString
public class PublicationName {
    String publicationName;

    public PublicationName(String publicationName) {
        //この時点では「雑誌名=英文の雑誌名」となることがありかなり長くなりうる
        if(publicationName.length() > 200) throw new IllegalArgumentException("掲載誌名が長すぎます。");
        this.publicationName = publicationName;
    }

    public String get() { return getDeletedAfterEqual();}
    public String getWithDoubleBrackets() {
        return "『" + getDeletedAfterEqual() + "』";
    }

    /**
     * 英文雑誌名が付さているとき参考文献としては不要なため削除
     * @return =以降が削除された雑誌名　
     */
    public String getDeletedAfterEqual() {
        if(!publicationName.contains("=")) return publicationName;

        int index = publicationName.indexOf("=");
        return publicationName.substring(0, index).trim();
    }

    public static PublicationName of(String publicationName) {
        return new PublicationName(publicationName);
    }

}
