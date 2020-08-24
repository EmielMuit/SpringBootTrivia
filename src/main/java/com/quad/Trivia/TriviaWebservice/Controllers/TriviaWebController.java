package com.quad.trivia.triviawebservice.controllers;

import com.quad.trivia.triviawebservice.helpers.Consts;
import com.quad.trivia.triviawebservice.helpers.TriviaHelper;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TriviaWebController {

    @GetMapping(Consts.ROOTURL)
    public String index(Model model, HttpSession session) {
        if (!session.isNew()) {
            // Restore form
            model.addAttribute(Consts.MODELFORMVARIABLENAME, (String) session.getAttribute(Consts.MODELFORMVARIABLECONTENT));
        } else {
            new TriviaHelper().fetchTrivia(session);
            // Display form
            model.addAttribute(Consts.MODELFORMVARIABLENAME, (String) session.getAttribute(Consts.MODELFORMVARIABLECONTENT));
        }
        return Consts.HOMEPAGE;
    }
}
