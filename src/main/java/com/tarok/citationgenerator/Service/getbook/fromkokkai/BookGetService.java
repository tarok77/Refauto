package com.tarok.citationgenerator.service.getbook.fromkokkai;

import com.tarok.citationgenerator.controller.forview.BookForView;
import com.tarok.citationgenerator.repository.RawBook;
import com.tarok.citationgenerator.service.makeurl.BookOrArticle;
import com.tarok.citationgenerator.service.makeurl.URLMaker;
import com.tarok.citationgenerator.service.makeurl.With;
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
import java.util.Objects;

@Service
@Slf4j
public class BookGetService {
    private final URLMaker URLmaker;
    private final FromEventToBook fromEventToBook;
    private final OkHttpClient client;

    public BookGetService(URLMaker urlMaker, FromEventToBook fromEventToBook, OkHttpClient client) {
        this.URLmaker = urlMaker;
        this.fromEventToBook = fromEventToBook;
        this.client = client;
    }

    public List<BookForView> getBookForViewList(With with, String searchInfo) throws XMLStreamException, IOException {
        URLmaker.changeStrategy(BookOrArticle.BOOK);
        String url = URLmaker.makeURL(with, searchInfo);

        return executeRequest(url).stream().map(BookForView::ConvertForView).toList();
    }

    public List<BookForView> getBookForViewList(With with, String title, String author) throws XMLStreamException, IOException {
        URLmaker.changeStrategy(BookOrArticle.BOOK);
        String url = URLmaker.makeURL(with, title, author);
        return executeRequest(url).stream().map(BookForView::ConvertForView).toList();
    }

    //共通の実行処理 okhttpによるアクセスとXML処理FromEventToBookの呼び出し
    public List<RawBook> executeRequest(String url) throws IOException, XMLStreamException {
        //TODO 開発中のデータ比較用　本番環境ではとる
        log.info(url);

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute();
             //XMLEventReaderはclosableではないのでここにかけない。
             InputStream is = Objects.requireNonNull(response.body()).byteStream()) {

            if (!response.isSuccessful()) throw new IOException("ヘッダーが失敗を通知" + response);

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
            log.warn("InputStreamで問題が発生", e);
            throw e;
        }
    }
}
