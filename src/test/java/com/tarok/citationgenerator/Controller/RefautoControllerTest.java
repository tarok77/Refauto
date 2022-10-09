package com.tarok.citationgenerator.Controller;

import com.sun.xml.bind.v2.TODO;
import com.tarok.citationgenerator.CitationGeneratorApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(classes = CitationGeneratorApplication.class)
class RefautoControllerTest {
    @Autowired
    private MockMvc mock;

    @Test
    void home() throws Exception {
        this.mock.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void submitIsbn() throws Exception {
        this.mock.perform(post("/submit/isbn").param("isbn", "1234567890")).
                andDo(print()).andExpect(status().isOk());
    }

    @Test
    void submitTitleAndAuthor() throws Exception {
        this.mock.perform(post("/submit/title").param("title", "美術の物語").param("author", "")).
                andDo(print()).andExpect(status().isOk());
    }

    @Test
    void articleSubmitでタイトルだけを送るとき() throws Exception {
        this.mock.perform((post("/article/submit")).param("title", "ヴァレリー").param("author", ""))
                .andExpect(status().isOk());
    }

    @Test
    void articleSubmitで著者だけを送る場合() throws Exception {
        this.mock.perform((post("/article/submit")).param("title", "").param("author", "レオ・シュトラウス"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void articleSubmitでタイトルと著者名を送るとき() throws Exception {
        this.mock.perform((post("/article/submit")).param("title", "時間").param("author", "中島"))
                .andDo(print()).andExpect(status().isOk());
    }
}