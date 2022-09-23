package com.tarok.citationgenerator.Controller;

import com.tarok.citationgenerator.Controller.Form.ISBNForm;
import com.tarok.citationgenerator.Controller.Form.TitleAndAuthor;
import com.tarok.citationgenerator.Repository.Book;
import com.tarok.citationgenerator.Repository.RawBook;
import com.tarok.citationgenerator.Service.httpAccess.BookGetService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.extract.spi.TableInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class QuoteController {
    private final BookGetService bookGetService;

    public QuoteController(BookGetService bookGetService) {
        this.bookGetService = bookGetService;
    }

    @ModelAttribute ("isbn")
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

    @GetMapping("/")
    public String home(@ModelAttribute("isbn")ISBNForm isbn, @ModelAttribute TitleAndAuthor titleAndAuthor) {
        return "home";
    }

    @PostMapping("/submit/isbn")
    //isbnでも二つのレコードが帰る可能性がある　使いまわしと登録データの詳細さの違い
    //例　https://iss.ndl.go.jp/api/sru?operation=searchRetrieve&maximumRecords=10&query=isbn=9784274226298
    //@ISBNのValidationがハイフン交じりなどをはじいてしまうため使わないでおく
    public String submitIsbn(@Validated @ModelAttribute("isbn")ISBNForm isbnForm,BindingResult result, Model model) throws IOException, XMLStreamException {
        //TODO メッセージをつける
        if(result.hasErrors()) return "home";
        //整形し、空のリクエストであれば受け付けずリダイレクト
        String isbn = isbnForm.getIsbn().replaceAll("-| ", "");
        if (isbn.equals("")) return "redirect:/";

        log.info(isbn);
        List<RawBook> rawBookList = bookGetService.getRawBookListByIsbn(isbn);
        if (rawBookList.isEmpty()) return "/noresult";
        //戻ってきた本が一種類に特定された場合ユーザーに選んでもらう画面遷移をとばす
        if (rawBookList.size() == 1) {
            Book book = Book.format(rawBookList.get(0));
            model.addAttribute("bookinfo", book.convertAPAReference());
            return "/citedbook";
        }
        //複数返ってきたときはユーザーに選んでもらうためリストにしてビューに送る
        List<BookForView> bookList = rawBookList.stream().map(BookForView::ConvertToView).toList();
        model.addAttribute("list", bookList);

        return "/books";
    }

    @PostMapping("/submit/title")
    public String submitTitle(@Validated @ModelAttribute TitleAndAuthor titleAndAuthor, BindingResult result, Model model) throws IOException, XMLStreamException {
        //TODO　メッセージをつける
        if(result.hasErrors()) return "home";

        String title = titleAndAuthor.getTitle();
        String author = titleAndAuthor.getAuthor();

        if (title.isBlank() && author.isBlank()) return "redirect:/";
        //TODO NPE確認
        List<RawBook> rawBookList;
        if (author.isBlank()) {
            rawBookList = bookGetService.getRawBookListByTitle(title);
        } else if (title.isBlank()) {
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

        List<BookForView> bookList = rawBookList.stream().map(BookForView::ConvertToView).toList();

        model.addAttribute("list", bookList);

        return "/books";
    }

    @PostMapping("/confirmed")
    public String show(@ModelAttribute("form") BookForView form, Model model) {
        var book = Book.of(form.getTitle(), form.getCreators(),form.getPublishedYear(),
                form.getPublisher(), form.getIsbn());

        var bookInfo = book.convertAPAReference();
        model.addAttribute("bookinfo", bookInfo);
        return "/citedbook";
    }
}
//9784274226298
//9784048930598
//9784121600820
