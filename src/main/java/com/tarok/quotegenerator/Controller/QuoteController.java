package com.tarok.quotegenerator.Controller;

import com.tarok.quotegenerator.Repository.Book;
import com.tarok.quotegenerator.Service.BookSetter;
import com.tarok.quotegenerator.Service.GetBookService;
import com.tarok.quotegenerator.Service.OkhttpForGoogleApi;
import com.tarok.quotegenerator.Service.OkhttpForKokkaiApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

@Controller
public class QuoteController {
    private final OkhttpForGoogleApi http;
    private final OkhttpForKokkaiApi  httpForKokkai = new OkhttpForKokkaiApi();
    private final GetBookService service = new GetBookService();

    public QuoteController(OkhttpForGoogleApi http) {
        this.http = http;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @PostMapping("/submit")
    public String submit(@RequestParam("ISBN") String originalIsbn) throws IOException, XPathExpressionException {
        String isbn = originalIsbn.replaceAll("-","").replaceAll(" ", "");
        System.out.println(isbn);
//        http.getJsonFromGoogle(isbn);
//        httpForKokkai.getXMLFromKokkai(isbn);
        service.getNodesByISBN(isbn);
        BookSetter setter = new BookSetter();
        Book book = setter.setAuthors(isbn);
        System.out.println(book);
//TODO　共著の場合の分割をメソッド化する必要
        System.out.println(book.getAuthors().getOrRepresentAuthor());
        return "redirect:/";
    }
}
//9784274226298
//9784048930598
//9784121600820
