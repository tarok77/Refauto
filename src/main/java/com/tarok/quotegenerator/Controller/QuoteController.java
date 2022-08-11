package com.tarok.quotegenerator.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuoteController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
