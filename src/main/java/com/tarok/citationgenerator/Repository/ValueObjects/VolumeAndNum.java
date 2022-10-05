package com.tarok.citationgenerator.Repository.ValueObjects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolumeAndNum {
    String volume;
    String num;

    //早期リターンを使うためにスタティックファクトリメソッドをつかう。コンストラクタの外部での使用を禁じる。
    private VolumeAndNum() {}

    public static VolumeAndNum of(String input) {
        var volumeAndNum = new VolumeAndNum();
        if(input.equals("NO_DATA")) {
            volumeAndNum.setVolume("NO_DATA");
            volumeAndNum.setNum("NO_DATA");

            return volumeAndNum;
        }
        if(!input.contains("(")) {
            volumeAndNum.setVolume(input);
            volumeAndNum.setNum("NO_DATA");
            return volumeAndNum;
        }

        int start = input.indexOf("(");
        int end = input.indexOf(")");

        String tmpVolume =input.substring(0, start);
        String tmpNum = input.substring(start + 1,end);

        volumeAndNum.setVolume(tmpVolume);
        volumeAndNum.setNum(tmpNum);

        //数値が存在しないとき0を入れている元データへの対策
        if(tmpVolume.equals("0"))  volumeAndNum.setVolume("NO_DATA");
        if(tmpNum.equals("0")) volumeAndNum.setNum("NO_DATA");

        return volumeAndNum;
    }
    public String get() {
        if(volume.equals("NO_DATA") && num.equals("NO_DATA")) return "NO_DATA";
        if(num.equals("NO_DATA")) return volume;
        if(volume.equals("NO_DATA")) return num;
        return volume + "(" + num + ")";
    }
    public String getVolumeAndNumInJapanese() {
        String tmp = get();
        if(!tmp.contains("(")) return tmp + "巻";
        return tmp.replaceFirst("\\(|（","巻").replaceFirst("\\)|）","号");
    }

//TODO 数字が同じとき警告を発する

}
