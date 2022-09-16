package com.tarok.citationgenerator.Controller;

import com.tarok.citationgenerator.Repository.Book;
import com.tarok.citationgenerator.Repository.RawBook;
import com.tarok.citationgenerator.Service.httpAccess.BookGetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

@Controller
public class QuoteController {
    private final BookGetService bookGetService;

    public QuoteController(BookGetService bookGetService) {
        this.bookGetService = bookGetService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/submit/isbn")
    //isbnでも二つのレコードが帰る可能性がある　使いまわしと登録データの詳細さの違い
    //例　https://iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=10&query=isbn=9784274226298
    public String submitIsbn(@RequestParam("ISBN") String IsbnFromHTML, Model model) throws IOException, XMLStreamException {
        //整形し、空のリクエストであれば受け付けずリダイレクト
        String isbn = IsbnFromHTML.replaceAll("-| ", "");
        if (isbn.equals("")) return "redirect:/";

        System.out.println(isbn);
        List<RawBook> rawBookList = bookGetService.getRawBookListByIsbn(isbn);
        if (rawBookList.isEmpty()) return "/noresult";
        //戻ってきた本が一種類に特定された場合ユーザーに選んでもらう画面遷移をとばす
        if (rawBookList.size() == 1) {
            Book book = Book.format(rawBookList.get(0));
            model.addAttribute("bookinfo", book.convertAPAReference());
            return "/citedbook";
        }
        //複数返ってきたときはユーザーに選んでもらうためリストにしてビューに送る
        List<BookForView> bookList = rawBookList.stream().map(BookForView::toView).toList();
        model.addAttribute("list", bookList);

        return "/books";
    }

    @PostMapping("/submit/title")
    //TODO modelandviewとの違い
    public String submitTitle(@RequestParam("title") String title, @RequestParam("author") String author, Model model) throws IOException, XMLStreamException {
        if (title.isBlank() && author.isBlank()) return "redirect:/";
        //TODO NPE確認
        List<RawBook> rawBookList;
        if(author.isBlank()) {
            rawBookList = bookGetService.getRawBookListByTitle(title);
        } else if(title.isBlank()) {
            rawBookList = bookGetService.getRawBookListByAuthor(author);
        } else {
            rawBookList = bookGetService.getRawBookListByTitleAndAuthor(title, author);
        }
        //リストが空であるときの対応
        if (rawBookList.isEmpty()) return "/noresult";
        //戻ってきた本が一種類に特定された場合ユーザーに選んでもらう画面遷移をとばす
        if (rawBookList.size() == 1) {
            Book book = Book.format(rawBookList.get(0));
            model.addAttribute("bookinfo", book.convertAPAReference());
            return "/citedbook";
        }

        List<BookForView> bookList = rawBookList.stream().map(BookForView::toView).toList();

        model.addAttribute("list", bookList);

        return "/books";
    }

    @PostMapping("/confirmed")
    //TODO Formの受け取りをオブジェクトにすると受け渡し側オブジェクトと干渉するのか動作させることができない。いったんこれで。
    public String show(@RequestParam("title") String title, @RequestParam("creators") String creators,
                       @RequestParam("publishedYear") String year, @RequestParam("publisher") String publisher,
                       @RequestParam("isbn") String isbn, Model model) {
        var book = Book.of(title, creators, year, publisher, isbn);
        //TODO 取る
        System.out.println(book);
        var bookInfo = book.convertAPAReference();
        model.addAttribute("bookinfo", bookInfo);
        return "/citedbook";
    }
}
//9784274226298
//9784048930598
//9784121600820
