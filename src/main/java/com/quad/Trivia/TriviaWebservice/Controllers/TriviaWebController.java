package com.quad.trivia.triviawebservice.controllers;

import com.quad.trivia.triviawebservice.helpers.TriviaFetcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TriviaWebController {

    @GetMapping("/")
    public String index(Model model) throws JsonProcessingException {
        String content = new ObjectMapper().writeValueAsString(new TriviaFetcher("5").fetchTrivia());
        model.addAttribute("content", content);
        return "index.html";
    }
}
