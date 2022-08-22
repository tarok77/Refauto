package com.tarok.quotegenerator.Service;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class OkhttpForKokkaiApi {
    private final OkHttpClient client = new OkHttpClient();
    private final URLMaker URLmaker = new URLMaker();

    public Document getDocumentFromKokkai(String isbn) throws IOException {

        String url = URLmaker.toKokkaiByISBN(isbn);
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
//            System.out.println(response.body().string());
            InputStream inputstream = response.body().byteStream();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputstream);

            return doc;

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void getXMLbyName(String name) {

    }
}
