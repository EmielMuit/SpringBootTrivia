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
        questions = new String[triviaRestResponse.getResults().size()];
        answers = new String[triviaRestResponse.getResults().size()][];
        for (int i = 0; i < triviaRestResponse.getResults().size(); i++)
        {
            questions[i] = triviaRestResponse.getResults().get(i).getQuestion();
            answers[i] = new String[triviaRestResponse.getResults().get(i).getIncorrect_answers().size()+1];
            for (int j = 0; j < triviaRestResponse.getResults().get(i).getIncorrect_answers().size(); j++)
            {
                answers[i][j] = triviaRestResponse.getResults().get(i).getIncorrect_answers().get(j);
                if (j == triviaRestResponse.getResults().get(i).getIncorrect_answers().size() -1)
                {
                    answers[i][j+1] = triviaRestResponse.getResults().get(i).getCorrect_answer();
                }
            }
            List<String> answersToShuffle = Arrays.asList(answers[i]);
            Collections.shuffle(answersToShuffle);
            answers[i] = answersToShuffle.toArray(answers[i]);
        }
    }
}
