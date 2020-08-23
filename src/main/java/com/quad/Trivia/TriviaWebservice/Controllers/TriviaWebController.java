package com.quad.trivia.triviawebservice.controllers;

import com.quad.trivia.triviawebservice.helpers.TriviaFetcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quad.trivia.triviawebservice.responses.TriviaRestResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class TriviaWebController {

    static final String CONTENT = "content";
    static final Logger log = LoggerFactory.getLogger(TriviaWebController.class);
    
    @GetMapping("/")
    public String index(Model model, HttpSession session) throws JsonProcessingException {
        String content = "";
        if (!session.isNew())
        {
            log.info("[web] NO NEW SESSION");
        }
        else
        {
            log.info("[web] NEW SESSION");
            final String url = "http://localhost:8080/questions";
            RestTemplate restTemplate = new RestTemplate();
            // Get the questions through the /questions endpoint
            TriviaRestResponse result = restTemplate.getForObject(url, TriviaRestResponse.class);
            // Create rewritten json from TriviaRestResponse
            content = new ObjectMapper().writeValueAsString(result.rewrite());
            log.info(content);
            session.setAttribute(CONTENT, content);
        }
        
        model.addAttribute(CONTENT, (String) session.getAttribute(CONTENT));
        return "index.html";
    }
}
