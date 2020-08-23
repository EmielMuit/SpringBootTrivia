package com.quad.trivia.triviawebservice.helpers;

import com.quad.trivia.triviawebservice.responses.RewrittenTriviaRestResponse;
import com.quad.trivia.triviawebservice.responses.TriviaRestResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.net.URL;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TriviaHelper {

    public TriviaRestResponse fetchTrivia(URL url)
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        TriviaRestResponse triviaRestResponse = new TriviaRestResponse();
        try {
            triviaRestResponse = mapper.readValue(url, new TypeReference<TriviaRestResponse>(){});
        } catch (IOException ex) {
            Logger.getLogger(TriviaHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return triviaRestResponse;
    }
    
    public RewrittenTriviaRestResponse rewriteTriviaRestResponse(TriviaRestResponse response)
    {
        RewrittenTriviaRestResponse rewrittenResponse = new RewrittenTriviaRestResponse(response);
        return rewrittenResponse;
    }
}
