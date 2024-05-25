package com.example.springbootclient.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.example.springbootclient.annotation.RestDocsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@DisplayName("SpringControllerTest API")
@WebMvcTest({SpringControllerTest.class})
@Import(SpringController.class)
@ContextConfiguration(classes = {SpringController.class})
@RestDocsTest
class SpringControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testMain() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/hello-world")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            //REST Docs 용
            .andDo(MockMvcRestDocumentation.document("version-1"))
            //OAS 3.0 - Swagger
            .andDo(MockMvcRestDocumentationWrapper.document("version-2"));
    }

}
