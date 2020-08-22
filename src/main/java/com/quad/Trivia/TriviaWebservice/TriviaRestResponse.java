package com.quad.Trivia.TriviaWebservice;

import java.util.List;

/**
 *
 * @author emiel
 */
public class TriviaRestResponse {
        
    private int response_code = -1;
    private List<Result> results;
    
    public int getResponse_code() {
        return response_code;
    }

    public List<Result> getResults() {
        return results;
    }
}
