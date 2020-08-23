package com.quad.trivia.triviawebservice.controllers;

import com.quad.trivia.triviawebservice.helpers.TriviaHelper;

import com.quad.trivia.triviawebservice.responses.RewrittenTriviaRestResponse;
import com.quad.trivia.triviawebservice.responses.TriviaRestResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class TriviaWebController {

    private final String CONTENT = "content";
    private final String REWRITTENCONTENT = "rewrittenContent";
    
    public String generateQuestionForm(RewrittenTriviaRestResponse rewrittenTriviaRestResponse)
    {
        String form = "<div>Questions:";
        for (int i = 0; i < rewrittenTriviaRestResponse.getQuestions().length; i++)
        {
            form += "<p class=\"triviaquestion\">Question " + (i + 1) + ", " + rewrittenTriviaRestResponse.getQuestions()[i] + "</br>";
            for (int j = 0; j < rewrittenTriviaRestResponse.getAnswers()[i].length; j++)
            {
                form += "<input ng-model=\"question.answer"+i+"\" ng-value=\""+j+"\" type=\"radio\">";
                form += "<label for=\"answer" + i + j + "\">" + rewrittenTriviaRestResponse.getAnswers()[i][j] + "</label></br>";
            }
            form += "</p>";
        }
        form += "</div>";
        return form;
    }
    
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        if (!session.isNew())
        {
            model.addAttribute(CONTENT, (String) session.getAttribute(REWRITTENCONTENT));
        }
        else
        {
            final String url = "http://localhost:8080/questions";
            RestTemplate restTemplate = new RestTemplate();
            // Get the question objects through the /questions endpoint
            TriviaRestResponse result = restTemplate.getForObject(url, TriviaRestResponse.class);
            // Create rewritten json from TriviaRestResponse
            RewrittenTriviaRestResponse rewrittenResult = new TriviaHelper().rewriteTriviaRestResponse(result);
            // Store actual questions/answers in session
            session.setAttribute(CONTENT, result);
            // Store rewritten json in session
            session.setAttribute(REWRITTENCONTENT, generateQuestionForm(rewrittenResult));
            // Generate form for rewritten trivia json
            model.addAttribute(CONTENT, generateQuestionForm(rewrittenResult));
        }
        return "index.html";
    }
}
