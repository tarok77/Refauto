package com.tarok.citationgenerator.Service.httpAccess;

import com.tarok.citationgenerator.Repository.RawBook;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class BookGetService {
    private final URLMaker URLmaker;
    private final FromEventToBook fromEventToBook;
    private final OkHttpClient client = new OkHttpClient();

    public BookGetService(URLMaker maker, FromEventToBook fromEventToBook) {
        this.URLmaker = maker;
        this.fromEventToBook = fromEventToBook;
    }

    //インプットの値に合わせたメソッドの実行群
    public List<RawBook> getRawBookListByTitle(String title) throws XMLStreamException, IOException {
        String url = URLmaker.createURLByTitle(title);
        return executeRequest(url);
    }

    public List<RawBook> getRawBookListByAuthor(String author) throws XMLStreamException, IOException {
        String url = URLmaker.CreateURLByAuthor(author);
        return executeRequest(url);
    }

    public List<RawBook> getRawBookListByIsbn(String isbn) throws XMLStreamException, IOException {
        String url = URLmaker.createURLByISBN(isbn);
        return executeRequest(url);
    }

    public List<RawBook> getRawBookListByTitleAndAuthor(String title, String author) throws XMLStreamException, IOException {
        String url = URLmaker.createURLByTitleAndAuthor(title, author);
        return executeRequest(url);
    }

    //共通の実行処理 okhttpによるアクセスとXML処理FromEventToBookの呼び出し
    public List<RawBook> executeRequest(String url) throws IOException, XMLStreamException {
        //TODO 開発中のデータ比較用　本番環境ではとる
        log.info(url);

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
                List<RawBook> bookList = fromEventToBook.createBookFromEventReader(reader);
                return bookList;

            } catch (XMLStreamException e) {
                log.warn("XMLの読み取りに失敗",e);
                throw e;
            } finally {
                if (!(reader == null)) reader.close();
            }
            //TODO 要検討
        } catch (IOException e) {
            log.warn("InputStreamの生成に失敗", e);
            throw e;
        }
    }
}
