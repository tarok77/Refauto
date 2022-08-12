package com.tarok.quotegenerator.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuoteController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
    @PostMapping("/submit")
    public String submit(@RequestParam("ISBN") String isbn) {
        System.out.println(isbn);
        return "redirect:/";
    }
}
