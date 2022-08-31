package com.tarok.quotegenerator.Service;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;

public class OkhttpForKokkaiApi {
    private final OkHttpClient client = new OkHttpClient();
    private final URLMaker URLmaker = new URLMaker();
    private final BookSetter bookSetter = new BookSetter();

//小さく分けたいがInputStreamを戻り値にするわけにもいかないようでエラーになる。イベント処理のほうを外部化するしかないのかな
//TODO メソッドを統一できたのでcreateURLに変更する

    public void getInputStreamFromKokkaiByISBN(String isbn) throws IOException, XMLStreamException {

        //受けっとったISBNからURLを作成しXML形式のレスポンスを取得する
        String url = URLmaker.toKokkaiByISBN(isbn);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute();
             InputStream is = response.body().byteStream()) {
            //XMLEventReaderはclosableではないのでここにかけない。

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            XMLEventReader reader = null;
            XMLInputFactory factory = XMLInputFactory.newInstance();
            try {
                reader = factory.createXMLEventReader(is);
                bookSetter.createBookFromEventReader(reader);
            }catch (XMLStreamException e) {
                e.printStackTrace();
                throw new XMLStreamException();
            } finally {
                if (!(reader == null)) reader.close();
            }
        //TODO 要検討
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        } //catch (XMLStreamException e) {
//            e.printStackTrace();
//            throw new XMLStreamException();
//        }
    }


    public void getXMLbyTitle(String title) throws IOException {
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
                        event = reader.nextEvent();
                        if (event.isCharacters()) System.out.println("タイトルは" + event.asCharacters());
                    }
                }
            }
            //TODO 失敗した場合のfinallyを追加べきか？
            reader.close();

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
