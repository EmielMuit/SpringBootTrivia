package com.quad.trivia.triviawebservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quad.trivia.triviawebservice.helpers.TriviaHelper;
import com.quad.trivia.triviawebservice.responses.TriviaRestResponse;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TriviaRestController {

    private final String TRIVIASOURCEURL = "https://opentdb.com/api.php?amount=";
    
    @GetMapping("/questions")
    public TriviaRestResponse triviaRestResponse(@RequestParam(value = "number", defaultValue = "5") String number) throws MalformedURLException
    {
        URL triviaSourceUrl = new URL(TRIVIASOURCEURL + number);
        return new TriviaHelper().fetchTrivia(triviaSourceUrl);
    }
    
    @PostMapping(path = "/checkanswers", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity checkAnswers(@RequestBody String answers, HttpSession session) throws JsonProcessingException {
        System.out.println("ENTERED:\n" + answers);
        int[] correctAnswers = (int[]) session.getAttribute("answers");
        int[] submittedAnswers = new ObjectMapper().readValue(answers, int[].class);
        
        boolean[] correctness = new boolean[correctAnswers.length];
        for (int i = 0; i < correctAnswers.length; i++)
        {
            correctness[i] = correctAnswers[i] == submittedAnswers[i];
        }
        
        return new ResponseEntity<>(correctness, HttpStatus.OK);
    }
}