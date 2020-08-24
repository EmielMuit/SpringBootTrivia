package com.quad.trivia.triviawebservice.helpers;

import com.quad.trivia.triviawebservice.responses.RewrittenTriviaRestResponse;
import com.quad.trivia.triviawebservice.responses.TriviaRestResponse;

import javax.servlet.http.HttpSession;

import org.springframework.web.client.RestTemplate;

public class TriviaHelper {

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

    // Fetches the trivia, stores the answers and HTML form in sessionstorage
    public RewrittenTriviaRestResponse fetchTrivia(HttpSession session) {
        RestTemplate restTemplate = new RestTemplate();
        TriviaRestResponse triviaRestResponse = restTemplate.getForObject(Consts.TRIVIASOURCEURL, TriviaRestResponse.class);

        RewrittenTriviaRestResponse rewrittenResponse = new RewrittenTriviaRestResponse(triviaRestResponse);
        this.storeAnswers(triviaRestResponse, rewrittenResponse, session);
        this.storeForm(rewrittenResponse, session);
        session.setAttribute(Consts.REWRITTENJSONSESSIONATTRIBUTENAME, rewrittenResponse);

        return rewrittenResponse;
    }

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
}
