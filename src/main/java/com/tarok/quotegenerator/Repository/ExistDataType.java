package com.tarok.quotegenerator.Repository;

/**
 * BOOKクラスが保有するデータの存在様態を表すクラス。和書の翻訳者など存在しないことが確定している場合NOT_EXISTと
 * XMLファイル内に存在しないため取得できないとき取得したデータの分類が未確定の時SUSPICIOUS、
 * データを確定できた時EXISTの三つに分ける　とりあえず翻訳者には持たせる
 */
public enum ExistDataType {
    NOT_EXIST, SUSPICIOUS, EXIST
}
