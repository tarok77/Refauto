package com.tarok.quotegenerator.Controller;

import com.tarok.quotegenerator.Service.Okhttp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuoteController {
    private final Okhttp http;

    public QuoteController(Okhttp http) {
        this.http = http;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @PostMapping("/submit")
    public String submit(@RequestParam("ISBN") String isbn) throws Exception {
        System.out.println(isbn);
        http.getJsonFromGoogle(isbn);
        return "redirect:/";
    }
}
//9784274226298
//9784048930598
