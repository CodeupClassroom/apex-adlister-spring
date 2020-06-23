package com.codeup.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ThreadLocalRandom;

@Controller
public class DiceRollController {
    @GetMapping("/roll-dice/{guess}")
    public String rollDice(@PathVariable int guess, Model model) {
        //Find a random number between 1 and 6.
        int randomNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        //Compare the guess to the random number.
        //Store if they guessed correctly in and attribute.
        model.addAttribute("isCorrectGuess", randomNum == guess);
        model.addAttribute("myGuess", guess);
        model.addAttribute("randomNumber", randomNum);
        return "roll-dice";
    }

    @GetMapping("/roll-dice")
    public String displayRollDice() {
        return "roll-dice";
    }
}
