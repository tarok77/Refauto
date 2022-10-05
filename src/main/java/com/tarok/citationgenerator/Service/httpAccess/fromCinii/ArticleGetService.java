package com.tarok.citationgenerator.Service.httpAccess.fromCinii;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarok.citationgenerator.Controller.ArticleForView;
import com.tarok.citationgenerator.Repository.Jackson.Items;
import com.tarok.citationgenerator.Repository.Jackson.JsonResponse;
import com.tarok.citationgenerator.Service.MakeURL.URLMaker;
import com.tarok.citationgenerator.Service.MakeURL.With;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.tarok.citationgenerator.Service.MakeURL.BookOrArticle.ARTICLE;

@Service
@Slf4j
public class ArticleGetService {
    URLMaker maker;
    private final OkHttpClient client;

    public ArticleGetService(URLMaker maker, OkHttpClient client) {
        this.maker = maker;
        this.client = client;
    }

    /**
     * 検索条件を受け取り論文のオブジェクトを返すというこのクラスの責務を果たすためのメソッド
     * クラス内の他のメソッドを使用する。
     *
     * @param with       検索条件の性質　タイトルか作者
     * @param searchInfo 　フォームへの入力値
     * @return jsonを解析しオブジェクトに変更したもののリスト
     * @throws IOException 　httpアクセスもしくはjsonの変換に失敗したとき　Viewで処理するために投げ直す。
     */
    public List<ArticleForView> getArticles(With with, String searchInfo) throws IOException {
        maker.changeStrategy(ARTICLE);
        String URL = maker.makeURL(with, searchInfo);
        log.info(URL);

        String json = executeRequest(URL);

        List<Items> tmp = convertArticleFromJson(json);
        return tmp.stream().map(ArticleForView::of).toList();
    }

    /**
     * 検索条件を受け取り論文のオブジェクトを返す、このクラスの責務を果たすためのメソッド
     * クラス内の他のメソッドを使用する。
     *
     * @param with   検索条件の性質　実際にはタイトルと作者のみのTITLE_AND_AUTHORしか使わないがオーバーロードするメソッドとの一貫性で入れている
     * @param title  フォームへの入力値　検索条件のタイトル
     * @param author フォームへの入力値　検索条件の作者名
     * @return jsonを解析しオブジェクトに変更したもののリスト
     * @throws IOException 　httpアクセスもしくはjsonの変換に失敗したとき　Viewで処理するために投げ直す。
     */
    public List<ArticleForView> getArticles(With with, String title, String author) throws IOException {
        maker.changeStrategy(ARTICLE);
        String URL = maker.makeURL(with, title, author);

        String json = executeRequest(URL);

        List<Items> tmp = convertArticleFromJson(json);
        return tmp.stream().map(ArticleForView::of).toList();
    }

    public String executeRequest(String URL) throws IOException {
        Request request = new Request.Builder().url(URL).build();
        String jsonString;

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("ヘッダーが失敗を通知" + response);

            jsonString = Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            log.warn("http requestでエラーが発生", e);
            throw e;
        }

        return jsonString;
    }

    public List<Items> convertArticleFromJson(String json) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var articles = mapper.readValue(json, JsonResponse.class);

        return articles.getItems();
    }
}

