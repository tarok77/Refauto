package com.tarok.quotegenerator.Service;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class OkhttpForKokkaiApi {
    private final OkHttpClient client = new OkHttpClient();

    public void getXMLFromKokkai(String isbn) throws IOException {
        String query = "isbn=\"" + isbn + "\"";
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        //maximumRecordは本番環境で要調整
        String url = "https://iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=10&recordSchema=dcndl&onlyBib=true&recordPacking=xml&query="
                + encodedQuery +
                " AND dpid=iss-ndl-opac";


        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            //上記のif文でNullPointerExceptionは回避できているか
            System.out.println(response.body().string());
        }
    }
}
