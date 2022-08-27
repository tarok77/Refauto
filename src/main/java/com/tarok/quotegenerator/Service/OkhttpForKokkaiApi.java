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
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class OkhttpForKokkaiApi {
    private final OkHttpClient client = new OkHttpClient();
    private final URLMaker URLmaker = new URLMaker();

//小さく分けたいがInputStreamを戻り値にするわけにもいかないようでエラーになる。イベント処理のほうを外部化するしかないのかな
    public void getInputStreamFromKokkaiByIsbn(String isbn) throws IOException {

        //受けっとったISBNからURLを作成しXML形式のレスポンスを取得する
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

            //レスポンスをEventReaderに変換
            InputStream is = response.body().byteStream();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader reader = factory.createXMLEventReader(is);

            //TODO ここを別クラスにとりだす
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement el = event.asStartElement();
                    if (el.getName().getLocalPart().equals("creator")) {
                        event = reader.nextEvent();
                        if (event.isCharacters()) System.out.println("作者は" + event.asCharacters());
                    }

                    if (el.getName().getLocalPart().equals("title")) {
                        System.out.print("title: ");
                        event = reader.nextEvent();
                        if (event.isCharacters()) System.out.println("タイトルは" + event.asCharacters());
                    }
                }
            }
            reader.close();

        } catch (XMLStreamException e) {
            e.printStackTrace();
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
