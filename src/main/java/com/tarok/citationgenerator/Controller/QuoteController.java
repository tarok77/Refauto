package com.tarok.citationgenerator.Controller;

import com.tarok.citationgenerator.Repository.Book;
import com.tarok.citationgenerator.Repository.RawBook;
import com.tarok.citationgenerator.Service.candelete.GetBookService;
import com.tarok.citationgenerator.Service.candelete.OkhttpForGoogleApi;
import com.tarok.citationgenerator.Service.httpAccess.OkhttpForKokkaiApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

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
    //TODO modelandviewとの違い
    public String submitTitle(@RequestParam("title") String titleFromHTML, Model model) throws IOException, XMLStreamException {
        String title = titleFromHTML.replaceAll(" ", "");
        if (title.equals("")) return "redirect:/";

        List<RawBook> rawBookList = httpForKokkai.getRawBookFromKokkai(title);
        //リストが空であるときの対応
        if (rawBookList.isEmpty()) return "/noresult";

        List<BookForView> bookList = rawBookList.stream().map(BookForView::toView).toList();

        model.addAttribute("list", bookList);

        return "/books";
    }

    @PostMapping("/confirmed")
    //TODO Formの受け取りをオブジェクトにすると受け渡し側オブジェクトと干渉するのか動作させることができない。いったんこれで。
    public String show(@RequestParam("title") String title, @RequestParam("creators") String creators,
                       @RequestParam("publishedYear") String year, @RequestParam("publisher") String publisher,
                       @RequestParam("isbn") String isbn, Model model) {
        var book = Book.of(title,creators,year,publisher,isbn);
        var bookInfo = book.convertAPAReference();
        model.addAttribute("bookinfo", bookInfo);
        return "/citedbook";
    }
}
//9784274226298
//9784048930598
//9784121600820
