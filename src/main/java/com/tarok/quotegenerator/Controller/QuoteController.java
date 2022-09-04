package com.tarok.quotegenerator.Controller;

import com.tarok.quotegenerator.Service.candelete.GetBookService;
import com.tarok.quotegenerator.Service.candelete.OkhttpForGoogleApi;
import com.tarok.quotegenerator.Service.httpAccess.OkhttpForKokkaiApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

@Controller
public class QuoteController {
    private final OkhttpForGoogleApi http;
    private final OkhttpForKokkaiApi httpForKokkai = new OkhttpForKokkaiApi();
    private final GetBookService service = new GetBookService();

    public QuoteController(OkhttpForGoogleApi http) {
        this.http = http;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/submit/isbn")
    //isbnでも二つのレコードが帰る可能性があるかも そういえば岩波文庫の使いまわし問題などもあったのでidとして機能していないかも知れない。
    //タイトルだけでも複数　and 検索ができるapiかどうか調べる
    //例　https://iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=10&query=isbn=9784274226298
    public String submitIsbn(@RequestParam("ISBN") String IsbnFromHTML) throws IOException, XMLStreamException {
        String isbn = IsbnFromHTML.replaceAll("-", "").replaceAll(" ", "");
        if (isbn.equals("")) return "redirect:/";

        System.out.println(isbn);
        httpForKokkai.getRawBookFromKokkai(isbn);

        return "redirect:/";
    }

    @PostMapping("/submit/title")
    public String submitTitle(@RequestParam("title") String titleFromHTML) throws IOException, XMLStreamException {
        String title = titleFromHTML.replaceAll(" ", "");
        if (title.equals("")) return "redirect:/";
        httpForKokkai.getRawBookFromKokkai(title);

        return "redirect:/";
    }
}
//9784274226298
//9784048930598
//9784121600820
