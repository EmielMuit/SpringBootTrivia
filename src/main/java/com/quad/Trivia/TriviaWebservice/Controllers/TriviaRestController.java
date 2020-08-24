package com.quad.trivia.triviawebservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.quad.trivia.triviawebservice.helpers.Consts;
import com.quad.trivia.triviawebservice.helpers.TriviaHelper;
import com.quad.trivia.triviawebservice.responses.RewrittenTriviaRestResponse;

import java.net.MalformedURLException;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TriviaRestController {

    @GetMapping(Consts.QUESTIONMAPPING)
    public RewrittenTriviaRestResponse triviaRestResponse(HttpSession session) throws MalformedURLException {
        if (session.isNew()) {
            // If the user decides to fetch questions with /GET on /questions, store this in a session
            TriviaHelper t = new TriviaHelper();
            return t.fetchTrivia(session);
        } else {
            // Otherwise it is in session storage, return this instead
            return (RewrittenTriviaRestResponse) session.getAttribute(Consts.REWRITTENJSONSESSIONATTRIBUTENAME);
        }
    }

    @PostMapping(path = Consts.CHECKANSWERSMAPPING, consumes = Consts.CONTENTTYPE, produces = Consts.CONTENTTYPE)
    public @ResponseBody
    ResponseEntity checkAnswers(@RequestBody String answers, HttpSession session) {
        try {
            // Get correct answers from session
            int[] correctAnswers = (int[]) session.getAttribute(Consts.ANSWERARRAYSESSIONATTRIBUTENAME);
            // Get submitted answers as array
            int[] submittedAnswers = new ObjectMapper().readValue(answers, int[].class);

            // Create boolean array describing correctness
            boolean[] correctness = new boolean[correctAnswers.length];
            for (int i = 0; i < correctAnswers.length; i++) {
                correctness[i] = correctAnswers[i] == submittedAnswers[i];
            }

            // Renew trivia!
            TriviaHelper t = new TriviaHelper();
            t.fetchTrivia(session);

            // Return correctness array
            return new ResponseEntity<>(correctness, HttpStatus.OK);
        }
        catch (Exception e)
        {
            // Is someone cheating?
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
    }
}
