package com.quad.trivia.triviawebservice.responses;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RewrittenTriviaRestResponse {

    String[] questions;
    String[][] answers;
    
    public String[] getQuestions() {
        return questions;
    }

    public String[][] getAnswers() {
        return answers;
    }
    
    public RewrittenTriviaRestResponse(TriviaRestResponse triviaRestResponse)
    {
        int numberOfQuestions = triviaRestResponse.getResults().size();
        questions = new String[numberOfQuestions];
        answers = new String[numberOfQuestions][];
        for (int i = 0; i < numberOfQuestions; i++)
        {
            Result currentQuestion = triviaRestResponse.getResults().get(i);
            questions[i] = currentQuestion.getQuestion();
            answers[i] = new String[currentQuestion.getIncorrect_answers().size()+1];
            int j;
            for (j = 0; j < currentQuestion.getIncorrect_answers().size(); j++)
            {
                answers[i][j] = currentQuestion.getIncorrect_answers().get(j);
            }
            answers[i][j] = currentQuestion.getCorrect_answer();
            List<String> answersToShuffle = Arrays.asList(answers[i]);
            Collections.shuffle(answersToShuffle);
            answers[i] = answersToShuffle.toArray(answers[i]);
        }
    }
}
