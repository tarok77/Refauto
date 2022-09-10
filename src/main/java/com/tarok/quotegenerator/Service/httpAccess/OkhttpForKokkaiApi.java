package com.tarok.quotegenerator.Service.httpAccess;

import com.tarok.quotegenerator.Repository.RawBook;
import com.tarok.quotegenerator.Service.BookGetter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OkhttpForKokkaiApi {
    private final OkHttpClient client = new OkHttpClient();
    private final URLMaker URLmaker = new URLMaker();
    private final BookGetter bookGetter = new BookGetter();

//小さく分けたいがInputStreamを戻り値にするわけにもいかないようでエラーになる。イベント処理のほうを外部化するしかないのかな

    public List<RawBook> getRawBookFromKokkai(String inputtedData) throws IOException, XMLStreamException {

        //受けっとったISBNからURLを作成しXML形式のレスポンスを取得する
        String url = URLmaker.createURL(inputtedData);
        //TODO 開発中のデータ比較用　本番環境ではとる
        System.out.println(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute();
             //TODO nullpoの警告が出ているので確かめる
             InputStream is = response.body().byteStream()) {
            //XMLEventReaderはclosableではないのでここにかけない。

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            XMLEventReader reader = null;
            XMLInputFactory factory = XMLInputFactory.newInstance();
            try {
                reader = factory.createXMLEventReader(is);
                List<RawBook> bookList = bookGetter.createBookFromEventReader(reader);
                return bookList;
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


//    public void getXMLbyTitle(String title) throws IOException {
//        String url = URLmaker.toKokkaiByTitle(title);
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            Headers responseHeaders = response.headers();
//            for (int i = 0; i < responseHeaders.size(); i++) {
//                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//            }
//
//            InputStream is = response.body().byteStream();
//
//            XMLInputFactory factory = XMLInputFactory.newInstance();
//            XMLEventReader reader = factory.createXMLEventReader(is);
//
//            while (reader.hasNext()) {
//                XMLEvent event = reader.nextEvent();
//                if (event.isStartElement()) {
//                    StartElement el = event.asStartElement();
//                    if (el.getName().getLocalPart().equals("creator")) {
//                        event = reader.nextEvent();
//                        if (event.isCharacters()) System.out.println("作者は" + event.asCharacters());
//                    }
//
//                    if (el.getName().getLocalPart().equals("title")) {
//                        event = reader.nextEvent();
//                        if (event.isCharacters()) System.out.println("タイトルは" + event.asCharacters());
//                    }
//                }
//            }

//            reader.close();
//
//        } catch (XMLStreamException e) {
//            e.printStackTrace();
//        }
//    }
}
