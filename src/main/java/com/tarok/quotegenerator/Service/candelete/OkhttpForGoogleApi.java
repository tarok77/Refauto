package com.tarok.quotegenerator.Service.candelete;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OkhttpForGoogleApi {
    private final OkHttpClient client = new OkHttpClient();

    public void getJsonFromGoogle(String isbn) throws IOException {
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:"
                + isbn;

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