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
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OkhttpForKokkaiApi {
    private final OkHttpClient client = new OkHttpClient();
    private final URLMaker URLmaker = new URLMaker();

    public Document getDocumentFromKokkaiByIsbn(String isbn) throws IOException {

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
            //try-with-resourceがもう一個いるのか？
            InputStream inputstream = response.body().byteStream();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            BufferedInputStream bufferedIs = new BufferedInputStream(inputstream);
            Document doc = builder.parse(bufferedIs);

            return doc;

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Document getXMLbyTitle(String title) {
        String url = URLmaker.toKokkaiByTitle(title);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            InputStream inputstream = response.body().byteStream();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            BufferedInputStream bufferedIs = new BufferedInputStream(inputstream);
            Document doc = builder.parse(bufferedIs);

            return doc;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
