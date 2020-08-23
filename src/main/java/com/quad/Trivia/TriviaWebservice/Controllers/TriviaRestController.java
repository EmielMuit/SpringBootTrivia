package com.quad.trivia.triviawebservice.controllers;

import com.quad.trivia.triviawebservice.helpers.TriviaFetcher;
import com.quad.trivia.triviawebservice.responses.TriviaRestResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TriviaRestController {
    
    @GetMapping("/questions")
    public TriviaRestResponse triviaRestResponse(@RequestParam(value = "number", defaultValue = "5") String number)
    {
        return new TriviaFetcher(number).fetchTrivia();
    }
}