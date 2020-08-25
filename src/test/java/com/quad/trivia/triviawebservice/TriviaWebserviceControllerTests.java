package com.quad.trivia.triviawebservice;

import com.quad.trivia.triviawebservice.responses.RewrittenTriviaRestResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class TriviaWebserviceControllerTests {

    @LocalServerPort
    private int port;
    
    @Test
    /*
    Call to /questions should return 5 questions
    */
    public void questionsAPIReturnsFiveQuestions()
    {
        TestRestTemplate template = new TestRestTemplate();
        assertEquals(template.getForObject("http://localhost:" + port + "/questions", RewrittenTriviaRestResponse.class).getQuestions().length, 5);
    }
}
