package com.tarok.citationgenerator.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * xmlから抽出したRAWBOOKの形式をBOOKに適合するように直す。とくにcreatorフィールドとauthors,translatorsを適合させることを使命とする
 * 便宜上authorとtranslatorを合わせたものをcreatorとよんでいる
 */
public class CreatorsConverter {
    /**
     * rawBookのCreatorフィールドをBookに渡すためのCreatorPairに変換する
     *
     * @param raw 変換するCreatorフィールドを持つRawBookインスタンス
     * @return 分類が終わったCreatorPair
     */
    public CreatorPair convert(RawBook raw) {
        List<String> creatorList = raw.getCreatorList();
        var dividedAuthorAndTranslator = divideAuthorAndTranslator(creatorList);
        var divided = divideCreators(dividedAuthorAndTranslator);
        var grouped = groupCreators(divided);
        return grouped;
    }

    //古いデータではDavid Thomas, Andrew Hunt共著 ; 村上雅章訳のように著者と訳者が同じタグ内に併記されるため両者を分ける
    //処理データの長さはせいぜい二三このはず
    public List<String> divideAuthorAndTranslator(List<String> creatorList) {
        if (creatorList.isEmpty()) return creatorList;

        //順番を維持したいので新しいリストを作って順に詰め直している。パフォーマンス次第で要検討 なにかあやしい
        List<String> alternative = new ArrayList<>();
        for (String name : creatorList) {
            var newNames = name.split(";");
            alternative.addAll((Arrays.stream(newNames).map(String::trim).toList()));
        }
        return alternative;
    }

    //'著'の字が含まれるまでのリストをList<Author>に、それ以降'訳'の字が含まれるまでをList<translator>に収容
    //長さが二以上で'編'の字が含まれる場合引用表記で使わないので削除
    //次のような失敗　
    // Translator(translator=ロバート・S.コス 共著), Translator(translator=瀬谷啓介)] 一つ目にも著が付いてるのか？
    //<dc:creator>ロバート・C.マーチン 著</dc:creator>
    //<dc:creator>ジェームス・W.ニューカーク, ロバート・S.コス 共著</dc:creator>
    //<dc:creator>瀬谷啓介 訳</dc:creator>
    //例外的すぎると考えるか
    public CreatorPair groupCreators(List<String> creators) {
        if (creators.isEmpty()) return new CreatorPair();
                //new IllegalArgumentException("リストが空です");//TODO 例外でいいのか　要検討

        var creatorPair = new CreatorPair();
        int index = 0;
        for (; index < creators.size(); index++) {
            String author = creators.get(index);

            if (author.contains("著")) {
                creatorPair.addAuthor(author.replaceAll("著|共|", "").trim());
                index++;
                break;
            }
            creatorPair.addAuthor(author.trim());
        }
        for (; index < creators.size(); index++) {
            String translator = creators.get(index);
            if (creators.get(index).contains("訳")) {
                creatorPair.addTranslator(translator.replaceAll("訳","").trim());
                break;
            }

            creatorPair.addTranslator(translator);
            //TODO 監訳の時の情報がいるかも

        }
        return creatorPair;
    }

    //TODO 正規表現で著者グループと訳者グループを分ける

    //すでに著者と翻訳者はわかれているものとして設計する。','でつながったままの要素同士を分割して新しいリストに詰めて返す
    public static List<String> divideCreators(List<String> creators) {
        if (creators.isEmpty()) return creators;

        List<String> alternative = new ArrayList<>();
        for (String name : creators) {
            var newNames = name.split(",");
            alternative.addAll(Arrays.stream(newNames).map(String::trim).toList());
            //map(e -> e.replaceAll("共|著|訳", ""))
        }
        return alternative;
    }

}
