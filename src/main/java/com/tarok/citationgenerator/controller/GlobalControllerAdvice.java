package com.tarok.citationgenerator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * コントローラー側で拾いきれなかったものへの対策
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, Model model) {
        model.addAttribute("error", "");
        model.addAttribute("message", "Exceptionが発生しました");
        model.addAttribute("status", HttpStatus.BAD_GATEWAY);

        return "error";
    }
}
