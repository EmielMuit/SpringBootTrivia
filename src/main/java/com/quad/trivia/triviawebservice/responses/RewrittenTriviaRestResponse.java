package com.quad.trivia.triviawebservice.responses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RewrittenTriviaRestResponse {

    String[] questions;
    List<List<String>> answers;

    public String[] getQuestions() {
        return questions;
    }

    public List<List<String>> getAnswers() {
        return answers;
    }

    public RewrittenTriviaRestResponse(TriviaRestResponse triviaRestResponse) {
        int numberOfQuestions = triviaRestResponse.getResults().size();
        questions = new String[numberOfQuestions];
        answers = new ArrayList<>();
        for (int i = 0; i < numberOfQuestions; i++) {
            Result currentQuestion = triviaRestResponse.getResults().get(i);
            questions[i] = currentQuestion.getQuestion();
            ArrayList<String> currentAnswers = new ArrayList<>();
            answers.add(currentAnswers);
            int j;
            for (j = 0; j < currentQuestion.getIncorrect_answers().size(); j++) {
                currentAnswers.add(currentQuestion.getIncorrect_answers().get(j));
            }
            currentAnswers.add(currentQuestion.getCorrect_answer());
            Collections.shuffle(currentAnswers);
        }
    }
}
