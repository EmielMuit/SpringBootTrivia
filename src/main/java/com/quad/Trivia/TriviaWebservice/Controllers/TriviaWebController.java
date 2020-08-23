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
    
    private String generateQuestionForm(RewrittenTriviaRestResponse rewrittenTriviaRestResponse)
    {
        String form = "<div>Questions:";
        for (int i = 0; i < rewrittenTriviaRestResponse.getQuestions().length; i++)
        {
            // Generate question and answer radio buttons HTML
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
            // Restore form
            model.addAttribute(CONTENT, (String) session.getAttribute(REWRITTENCONTENT));
        }
        else
        {
            final String url = "http://localhost:8080/questions";
            RestTemplate restTemplate = new RestTemplate();
            // Get the question objects through the /questions endpoint
            TriviaRestResponse response = restTemplate.getForObject(url, TriviaRestResponse.class);
            // Create rewritten json from TriviaRestResponse
            RewrittenTriviaRestResponse rewrittenResponse = new TriviaHelper().rewriteTriviaRestResponse(response);
            
            //TO-DO store answers in session
            
            
            // Generate form for questions, store in session
            session.setAttribute(REWRITTENCONTENT, generateQuestionForm(rewrittenResponse));
            
            // Display form
            model.addAttribute(CONTENT, generateQuestionForm(rewrittenResponse));
        }
        return "index.html";
    }
}
