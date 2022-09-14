package com.tarok.citationgenerator.Controller;

import com.sun.xml.bind.v2.TODO;
import com.tarok.citationgenerator.CitationGeneratorApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(classes = CitationGeneratorApplication.class)
class QuoteControllerTest {
    @Autowired
    private MockMvc mock;

    @Test
    void home() throws Exception {
        this.mock.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void submitIsbn() throws Exception {
        this.mock.perform(post("/submit/isbn").param("ISBN", "1234567890")).
                andDo(print()).andExpect(status().isOk());
    }

    @Test
    void submitTitle() throws Exception {
        this.mock.perform(post("/submit/title").param("title", "美術の物語")).
                andDo(print()).andExpect(status().isOk());
    }

    //@TODO メソッドを変えてしまいのでいったんたいき
//    @Test
//    void show() throws Exception {
//    }

}