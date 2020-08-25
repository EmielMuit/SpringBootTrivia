package com.quad.trivia.triviawebservice.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.quad.trivia.triviawebservice.responses.RewrittenTriviaRestResponse;
import com.quad.trivia.triviawebservice.responses.TriviaRestResponse;

import javax.servlet.http.HttpSession;

import org.springframework.web.client.RestTemplate;

public class TriviaHelper {

    private static TriviaHelper triviaHelperInstance = null;
    
    private TriviaHelper(){}
    
    // Checks submitted answers for correctness, restarts a game!
    public boolean[] checkAnswers(String answers, HttpSession session) throws JsonProcessingException
    {
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
        this.fetchTrivia(session);
        
        return correctness;
    }
    
    // Fetches the trivia, stores the correct answers, trivia JSON and HTML form in sessionstorage
    public RewrittenTriviaRestResponse fetchTrivia(HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        TriviaRestResponse triviaRestResponse = restTemplate.getForObject(Consts.TRIVIASOURCEURL, TriviaRestResponse.class);
        RewrittenTriviaRestResponse rewrittenResponse = new RewrittenTriviaRestResponse(triviaRestResponse);
        
        this.storeAnswers(triviaRestResponse, rewrittenResponse, session);
        this.storeForm(rewrittenResponse, session);
        session.setAttribute(Consts.REWRITTENJSONSESSIONATTRIBUTENAME, rewrittenResponse);

        return rewrittenResponse;
    }
    
    // Generates the HTML form containing questions
    public String generateQuestionForm(RewrittenTriviaRestResponse rewrittenTriviaRestResponse) {
        String form = "<div>Questions:";
        for (int i = 0; i < rewrittenTriviaRestResponse.getQuestions().length; i++) {
            // Generate question and answer radio buttons HTML
            form = form.concat("<p class=\"triviaquestion\">Question " + (i + 1) + ", " + rewrittenTriviaRestResponse.getQuestions()[i] + "</br>");
            for (int j = 0; j < rewrittenTriviaRestResponse.getAnswers().get(i).size(); j++) {
                form = form.concat("<input ng-model=\"question.answer" + i + "\" ng-value=\"" + j + "\" type=\"radio\">");
                form = form.concat("<label for=\"answer" + i + j + "\">" + rewrittenTriviaRestResponse.getAnswers().get(i).get(j) + "</label></br>");
            }
            form = form.concat("</p>");
        }
        form = form.concat("</div>");
        return form;
    }

    // Stores correct answers indices as an int array in session
    public void storeAnswers(TriviaRestResponse response, RewrittenTriviaRestResponse rewrittenResponse, HttpSession session) {
        // Fill an array with indices of the correct, shuffled answers
        int[] answers = new int[response.getResults().size()];
        for (int i = 0; i < answers.length; i++) {
            answers[i] = rewrittenResponse.getAnswers().get(i).indexOf(response.getResults().get(i).getCorrect_answer());
        }
        // Store answers in session
        session.setAttribute(Consts.ANSWERARRAYSESSIONATTRIBUTENAME, answers);
    }

    public void storeForm(RewrittenTriviaRestResponse rewrittenResponse, HttpSession session) {
        session.setAttribute(Consts.MODELFORMVARIABLECONTENT, this.generateQuestionForm(rewrittenResponse));
    }
    
    public static TriviaHelper getTriviaHelperInstance()
    {
        if (triviaHelperInstance == null)
        {
            triviaHelperInstance = new TriviaHelper();
        }
        return triviaHelperInstance;
    }
}
