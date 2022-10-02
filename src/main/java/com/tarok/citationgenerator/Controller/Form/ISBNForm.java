package com.tarok.citationgenerator.Controller.Form;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ISBNForm {
    @NotBlank(message = "数値を入力してください。")
    String isbn;

    //@ISBNのValidationがハイフン交じりなどをはじいてしまうための代用
    @AssertTrue(message = "ISBNは10桁か13桁です。")
    public boolean isISBNLength() {
        if(isbn.length()==10) return true;
        if(isbn.length()==13) return true;

        return false;
    }
}
