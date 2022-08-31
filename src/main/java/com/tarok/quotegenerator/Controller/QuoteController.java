package com.tarok.quotegenerator.Controller;

import com.tarok.quotegenerator.Service.candelete.GetBookService;
import com.tarok.quotegenerator.Service.candelete.OkhttpForGoogleApi;
import com.tarok.quotegenerator.Service.OkhttpForKokkaiApi;
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
        httpForKokkai.getInputStreamFromKokkaiByISBN(isbn);

        return "redirect:/";
    }

    @PostMapping("/submit/title")
    //新しいメソッドを使っていない可能性がある
    public String submitTitle(@RequestParam("title") String titleFromHTML) throws IOException {
        String title = titleFromHTML.replaceAll(" ", "");
        httpForKokkai.getXMLbyTitle(title);

        return "redirect:/";
    }
}
//9784274226298
//9784048930598
//9784121600820
