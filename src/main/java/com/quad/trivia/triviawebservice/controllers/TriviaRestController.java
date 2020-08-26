package com.quad.trivia.triviawebservice.controllers;

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
            // If the user decides to fetch questions with /GET on /questions, store this in a session before returning
            TriviaHelper.getTriviaHelperInstance().fetchTrivia(session);
        }
        return (RewrittenTriviaRestResponse) session.getAttribute(Consts.REWRITTENJSONSESSIONATTRIBUTENAME);
    }

    @PostMapping(path = Consts.CHECKANSWERSMAPPING, consumes = Consts.CONTENTTYPE, produces = Consts.CONTENTTYPE)
    public @ResponseBody
    ResponseEntity checkAnswers(@RequestBody String answers, HttpSession session) {
        try {
            // Return correctness array and renew trivia
            return new ResponseEntity<>(TriviaHelper.getTriviaHelperInstance().checkAnswers(answers, session), HttpStatus.OK);
        }
        catch (Exception e)
        {
            // Is someone cheating?
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
    }
}
