package com.quad.trivia.triviawebservice.responses;

import java.util.List;

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
