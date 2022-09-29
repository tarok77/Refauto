package com.tarok.citationgenerator.Repository.ValueObjects.creator.author;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class Author {
    private final String author;

    private Author(String author) throws IllegalArgumentException {
        //どこかで呼ばれてエラーで止まってしまう対策を考える
        if(author.length() == 0){throw new IllegalArgumentException("作者の名前が空です");}
        //共著の場合かなり長くなるので注意
        if(author.length() > 50){throw new IllegalArgumentException("作者の名前が長すぎます。");}
        this.author = author;
    }

    public static Author nameOf(String name) {
        return new Author(name);
    }

    public String getName() {
        return this.author;
    }

    /**
     * 外国人著者の場合名・姓で登録されているデータを姓・名に反転させてgetするためのメソッド。
     * 関連する"."や","の加筆をおこなう。
     * @return 反転後のauthorフィールドの値
     */
    public String getReversed() {
        List<String> tmp = List.of(author.trim().split(" |　|・"));
        //漢字圏もしくは姓か名しかない場合の早期リターン
        if(tmp.size()==1) return tmp.get(0);

        String reversed = tmp.get(tmp.size()-1) + ", ";
        //TODO　アルファベット大文字一文字なら.をつけるようにする
        for(int i = 0; i < tmp.size()-1; i++) {
            //長くて四要素、ほとんどは二要素であるため見やすさのためビルダーは使わない
            reversed += tmp.get(i);
            //イニシャルである場合"."をつけて"・"はつけない。詰まった感じになるのでスペースを入れておく。
            if(tmp.get(i).length()==1){
                reversed += ". ";
                continue;
            }
            if(i != tmp.size()-2) reversed += "・";
        }
        //イニシャル対策で入れたスペースが最後に入っていたらトリム
        return reversed.trim();
    }
}
