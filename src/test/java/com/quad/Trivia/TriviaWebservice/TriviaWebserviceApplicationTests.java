package com.quad.trivia.triviawebservice;

import com.quad.trivia.triviawebservice.controllers.TriviaWebController;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TriviaWebController.class)
class TriviaWebserviceApplicationTests {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    /*
    Homepage should contain the default message
     */
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("trivia quiz")));
    }
}
