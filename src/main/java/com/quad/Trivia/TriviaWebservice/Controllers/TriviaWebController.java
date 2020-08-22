package com.quad.Trivia.TriviaWebservice.Controllers;

import java.io.IOException; 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quad.Trivia.TriviaWebservice.Helpers.TriviaFetcher;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TriviaWebController {

    @GetMapping("/")
    public String index(Model model) {
        String content = "Failed to fetch json";
        try {
            content = new ObjectMapper().writeValueAsString(new TriviaFetcher("5").FetchTrivia());
        } catch (IOException ex) {
            Logger.getLogger(TriviaWebController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("content", content);
        return "index.html";
    }
}
