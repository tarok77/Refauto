package com.tarok.citationgenerator.Controller;

import com.tarok.citationgenerator.Controller.Form.ISBNForm;
import com.tarok.citationgenerator.Controller.Form.TitleAndAuthor;
import com.tarok.citationgenerator.Repository.Article;
import com.tarok.citationgenerator.Repository.Book;
import com.tarok.citationgenerator.Service.MakeURL.With;
import com.tarok.citationgenerator.Service.httpAccess.FromKokkai.BookGetService;
import com.tarok.citationgenerator.Service.httpAccess.fromCinii.ArticleGetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class RefautoController {
    private final BookGetService bookGetService;
    private final ArticleGetService articleGetService;

    public RefautoController(BookGetService bookGetService, ArticleGetService articleGetService) {
        this.bookGetService = bookGetService;
        this.articleGetService = articleGetService;
    }

    //マッピングのメソッド群のはじまり
    @GetMapping("/")
    public String home() {
        return "bookHome";
    }

    @GetMapping("/article")
    public String article() {
        return "articleHome";
    }

    @PostMapping("/submit/isbn")
    //isbnでも二つのレコードが帰る可能性がある　使いまわしと登録データの詳細さの違い
    public String submitIsbn(@Validated @ModelAttribute("isbn") ISBNForm isbnForm, BindingResult result, Model model) throws IOException, XMLStreamException {
        if (result.hasErrors()) return "bookhome";
        //整形し、空のリクエストであれば受け付けずリダイレクト
        String isbn = isbnForm.getIsbn().replaceAll("-| ", "");
        if (isbn.equals("")) return "redirect:/";

        //apiへアクセスし結果を下記リストに詰めて送る
        List<BookForView> bookList = bookGetService.getBookForViewList(With.ISBN, isbn);

        if (bookList.isEmpty()) return "noresult";
        //戻ってきた本が一種類に特定された場合ユーザーに選んでもらう画面遷移をとばす
        if (bookList.size() == 1) {
            Book book = Book.fromBookForView(bookList.get(0));
            model.addAttribute("bookinfo", book.convertAPAReference());
            return "citedbook";
        }
        //複数返ってきたときはユーザーに選んでもらうための専用viewに返す
        model.addAttribute("list", bookList);
        return "books";
    }

    @PostMapping("/submit/title")
    public String submitTitle(@Validated @ModelAttribute TitleAndAuthor titleAndAuthor, BindingResult result, Model model) throws IOException, XMLStreamException {
        if (result.hasErrors()) return "bookHome";

        String title = titleAndAuthor.getTitle();
        String author = titleAndAuthor.getAuthor();

        //フォームの両方が空欄の場合　Validationではじいているが念のため
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

            Map<String, String> bookMap = MapMakerForView.makeReferenceStylesAndPutThemOnMap(book);
            model.addAttribute("bookMap", bookMap);
            return "citedbook";
        }

        model.addAttribute("list", bookForViewList);

        return "books";
    }

    @PostMapping("/article/submit")
    public String showArticles(@Validated @ModelAttribute TitleAndAuthor titleAndAuthor, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) return "articleHome";

        String title = titleAndAuthor.getTitle();
        String author = titleAndAuthor.getAuthor();

        if (title.isBlank() && author.isBlank()) return "redirect:/articleHome";

        //apiへのアクセスの開始。下記リストに詰めて送る。
        List<ArticleForView> articleForViewList;
        if (author.isBlank()) {
            articleForViewList = articleGetService.getArticles(With.TITLE, title);
        } else if (title.isBlank()) {
            articleForViewList = articleGetService.getArticles(With.AUTHOR, author);
        } else {
            articleForViewList = articleGetService.getArticles(With.TITLE_AND_AUTHOR, title, author);
        }

        //リストが空であるときの対応
        if (articleForViewList.isEmpty()) return "noresult";

        model.addAttribute("articles", articleForViewList);
        return "articles";
    }

    //ユーザーが選んだ一冊を三つの参考文献スタイルに整形しmapに詰めて送る
    @PostMapping("/book/confirmed")
    public String showBookResult(@ModelAttribute("form") BookForView form, Model model) {
        var book = Book.fromBookForView(form);

        Map<String, String> bookMap = MapMakerForView.makeReferenceStylesAndPutThemOnMap(book);

        model.addAttribute("bookMap", bookMap);
        return "citedbook";
    }

    //ユーザーが選んだ論文を三つの参考文献スタイルに整形しmapに詰めて送る
    @PostMapping("/article/confirmed")
    public String showArticleResult(@ModelAttribute("articleForm") ArticleForView articleForView, Model model) {
        var article = Article.fromView(articleForView);
        Map<String, String> articleMap = MapMakerForView.makeReferenceStylesAndPutThemOnMap(article);

        model.addAttribute("articleMap", articleMap);
        return "citedArticle";
    }

    //Formクラスを使うためのメソッド群のはじまり
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

    //例外処理のメソッド群のはじまり
    //ログへの記録は発生元でおこなっている
    @ExceptionHandler(IOException.class)
    public String IOExceptionHandler(IOException e, Model model) {
        model.addAttribute("error", "");
        model.addAttribute("message", "データ変換の過程でエラーが発生しました。(IO)");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return "error";
    }

    @ExceptionHandler(XMLStreamException.class)
    public String XMLExceptionHandler(XMLStreamException e, Model model) {
        model.addAttribute("error", "");
        model.addAttribute("message", "データ変換の過程でエラーが発生しました。(XML)");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String OtherExceptionHandler(Exception e, Model model) {
        model.addAttribute("error", "");
        model.addAttribute("message", "データ変換の過程でエラーが発生しました。(other)");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);

        return "error";
    }
}
