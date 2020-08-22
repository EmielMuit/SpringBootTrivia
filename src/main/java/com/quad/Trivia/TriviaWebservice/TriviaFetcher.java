package com.quad.Trivia.TriviaWebservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.net.URL;

public class TriviaFetcher {
    
    private String numberOfQuestions;
    
    public TriviaFetcher(String aNrOfQuestions)
    {
        this.numberOfQuestions = aNrOfQuestions;
    }
    
    public TriviaRestResponse FetchTrivia() throws IOException
    {
        URL url = new URL("https://opentdb.com/api.php?amount=" + numberOfQuestions);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        TriviaRestResponse triviaRestResponse = mapper.readValue(url, new TypeReference<TriviaRestResponse>(){});
                
        return triviaRestResponse;
    }
}
