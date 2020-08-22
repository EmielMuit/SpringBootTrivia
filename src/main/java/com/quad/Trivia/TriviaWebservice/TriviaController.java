package com.quad.Trivia.TriviaWebservice;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TriviaController {

    @GetMapping("/questions")
    public TriviaRestResponse triviaRestResponse(@RequestParam(value = "number", defaultValue = "5") String number)
    {
        try {
            return new TriviaFetcher(number).FetchTrivia();
        } catch (IOException ex) {
            Logger.getLogger(TriviaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new TriviaRestResponse();
    }
}