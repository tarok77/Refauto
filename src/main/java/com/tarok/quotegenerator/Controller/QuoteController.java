package com.tarok.quotegenerator.Controller;

import com.tarok.quotegenerator.Service.OkhttpForGoogleApi;
import com.tarok.quotegenerator.Service.OkhttpForKokkaiApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class QuoteController {
    private final OkhttpForGoogleApi http;
    private final OkhttpForKokkaiApi  httpForKokkai = new OkhttpForKokkaiApi();

    public QuoteController(OkhttpForGoogleApi http) {
        this.http = http;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @PostMapping("/submit")
    public String submit(@RequestParam("ISBN") String isbn) throws IOException {
        System.out.println(isbn);
        http.getJsonFromGoogle(isbn);
        httpForKokkai.getXMLFromKokkai(isbn);
        return "redirect:/";
    }
}
//9784274226298
//9784048930598
