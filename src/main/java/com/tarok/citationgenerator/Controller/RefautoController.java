package com.tarok.citationgenerator.Controller;

import com.tarok.citationgenerator.Controller.Form.ISBNForm;
import com.tarok.citationgenerator.Controller.Form.TitleAndAuthor;
import com.tarok.citationgenerator.Repository.Book;
import com.tarok.citationgenerator.Service.MakeURL.With;
import com.tarok.citationgenerator.Service.httpAccess.FromKokkai.BookGetService;
import com.tarok.citationgenerator.Service.httpAccess.fromCinii.ArticleGetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
public class RefautoController {
    private final BookGetService bookGetService;
    private final ArticleGetService articleGetService;

    public RefautoController(BookGetService bookGetService, ArticleGetService articleGetService) {
        this.bookGetService = bookGetService;
        this.articleGetService = articleGetService;
    }

    //Formクラスを使うためのメソッド群
    @ModelAttribute("isbn")
    public ISBNForm setUpISBNForm() {
        return new ISBNForm();
    }

    @ModelAttribute
    public TitleAndAuthor setUpTitleAndAuthorForm() {
        return new TitleAndAuthor();
    }

    @ModelAttribute("form")
    public BookForView setUpFrom() {
        return new BookForView();
    }

    @ModelAttribute("articleForm")
    public ArticleForView setUpArticleForm() {return new ArticleForView();}

    //ここからマッピングのメソッド
    @GetMapping("/")
    public String home(@ModelAttribute("isbn") ISBNForm isbn, @ModelAttribute TitleAndAuthor titleAndAuthor) {
        return "home";
    }

    @GetMapping("/article")
    public String article(@ModelAttribute TitleAndAuthor titleAndAuthor) {
        return "articleHome";
    }

    @PostMapping("/submit/isbn")
    //isbnでも二つのレコードが帰る可能性がある　使いまわしと登録データの詳細さの違い
    //例　https://iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=10&query=isbn=9784274226298
    public String submitIsbn(@Validated @ModelAttribute("isbn") ISBNForm isbnForm, BindingResult result, Model model) throws IOException, XMLStreamException {
        //TODO メッセージをつける
        if (result.hasErrors()) return "home";
        //整形し、空のリクエストであれば受け付けずリダイレクト
        String isbn = isbnForm.getIsbn().replaceAll("-| ", "");
        if (isbn.equals("")) return "redirect:/";

        log.info(isbn);

        List<BookForView> bookList = bookGetService.getBookForViewList(With.ISBN, isbn);

        if (bookList.isEmpty()) return "noresult";
        //戻ってきた本が一種類に特定された場合ユーザーに選んでもらう画面遷移をとばす
        if (bookList.size() == 1) {
            Book book = Book.fromBookForView(bookList.get(0));
            model.addAttribute("bookinfo", book.convertAPAReference());
            return "citedbook";
        }
        //複数返ってきたときはユーザーに選んでもらうためリストにしてビューに送る
        model.addAttribute("list", bookList);

        return "books";
    }

    @PostMapping("/submit/title")
    public String submitTitle(@Validated @ModelAttribute TitleAndAuthor titleAndAuthor, BindingResult result, Model model) throws IOException, XMLStreamException {

        if (result.hasErrors()) return "home";

        String title = titleAndAuthor.getTitle();
        String author = titleAndAuthor.getAuthor();

        if (title.isBlank() && author.isBlank()) return "redirect:/";
        //TODO NPE確認
        List<BookForView> bookForViewList;
        if (author.isBlank()) {
            bookForViewList = bookGetService.getBookForViewList(With.TITLE, title);
        } else if (title.isBlank()) {
            bookForViewList = bookGetService.getBookForViewList(With.AUTHOR, author);
        } else {
            bookForViewList = bookGetService.getBookForViewList(With.TITLE_AND_AUTHOR, title, author);
        }
        //リストが空であるときの対応
        if (bookForViewList.isEmpty()) return "noresult";
        //戻ってきた本が一種類に特定された場合ユーザーに選んでもらう画面遷移をとばす
        if (bookForViewList.size() == 1) {
            Book book = Book.fromBookForView(bookForViewList.get(0));
            model.addAttribute("bookinfo", book.convertAPAReference());
            return "citedbook";
        }

        model.addAttribute("list", bookForViewList);

        return "books";
    }

    @PostMapping("/article/submit")
    public String showArticles(@Validated @ModelAttribute TitleAndAuthor titleAndAuthor, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) return "articleHome";
        log.info(titleAndAuthor.toString());

        String title = titleAndAuthor.getTitle();
        String author = titleAndAuthor.getAuthor();

        if (title.isBlank() && author.isBlank()) return "redirect:/articleHome";

        List<ArticleForView> articleForViewList;
        if (author.isBlank()) {
            articleForViewList = articleGetService.getArticles(With.TITLE, title);
        } else if (title.isBlank()) {
            articleForViewList = articleGetService.getArticles(With.AUTHOR, author);
        } else {
            articleForViewList = articleGetService.getArticles(With.TITLE_AND_AUTHOR, title, author);
        }

        model.addAttribute("articles", articleForViewList);
        return "articles";
    }

    @PostMapping("/confirmed")
    public String showBookResult(@ModelAttribute("form") BookForView form, Model model) {
        var book = Book.fromBookForView(form);

        var bookMap = new HashMap<>();
        var APABookInfo = book.convertAPAReference();
        var standardBookInfo = book.convertStandardReference();
        var ChicagoBookInfo = book.convertSIST02Reference();
        bookMap.put("APA", APABookInfo);
        bookMap.put("standard", standardBookInfo);
        bookMap.put("SIST02", ChicagoBookInfo);

        model.addAttribute("bookMap", bookMap);
        return "citedbook";
    }

    @PostMapping("/confirmedArticle")
    public String showArticleResult(@ModelAttribute("articleForm") ArticleForView articleForView, Model model) {
        System.out.println(articleForView);
        return "home";
    }
}
