package com.tarok.citationgenerator.Controller.Form;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ISBNForm {
    @NotBlank
    String isbn;

    @AssertTrue
    public boolean isISBNLength() {
        if(isbn.length()==10) return true;
        if(isbn.length()==13) return true;

        return false;
    }
}
