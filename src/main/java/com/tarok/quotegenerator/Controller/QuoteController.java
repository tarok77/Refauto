package com.tarok.quotegenerator.Controller;

import com.tarok.quotegenerator.Service.GetBookService;
import com.tarok.quotegenerator.Service.OkhttpForGoogleApi;
import com.tarok.quotegenerator.Service.OkhttpForKokkaiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

@Controller
public class QuoteController {
    private final OkhttpForGoogleApi http;
    private final OkhttpForKokkaiApi  httpForKokkai = new OkhttpForKokkaiApi();
    private final GetBookService service = new GetBookService();
//    private final GetBookFromInputStream bookGetter = new GetBookFromInputStream();

    public QuoteController(OkhttpForGoogleApi http) {
        this.http = http;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/submit/isbn")
    public String submitIsbn(@RequestParam("ISBN") String IsbnFromHTML) throws IOException, XMLStreamException {
        String isbn = IsbnFromHTML.replaceAll("-","").replaceAll(" ", "");
        System.out.println(isbn);
        httpForKokkai.getInputStreamFromKokkaiByIsbn(isbn);
//        bookGetter.getBook(isbn);
//        http.getJsonFromGoogle(isbn);
//        httpForKokkai.getXMLFromKokkai(isbn);
//        service.getNodesByISBN(isbn);
//        BookSetter setter = new BookSetter();
//        Book book = setter.setAuthors(isbn);
//        System.out.println(book);
//TODO　共著の場合の分割をメソッド化する必要

//        System.out.println("作者は" + book.getAuthorNames());
//        System.out.println("翻訳は" + book.getTranslator());
//        System.out.println(book.getAuthors().getOrRepresentAuthorsName());
        return "redirect:/";
    }

    @PostMapping("/submit/title")
    public String submitTitle(@RequestParam("title") String titleFromHTML) throws XPathExpressionException {
        String title = titleFromHTML.replaceAll(" ", "");
        service.getNodesByTitle(title);

        return "redirect:/";
    }
}
//9784274226298
//9784048930598
//9784121600820
