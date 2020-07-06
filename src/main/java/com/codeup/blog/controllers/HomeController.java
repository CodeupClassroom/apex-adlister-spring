package com.codeup.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "ads/index";
    }

    @GetMapping("/home")
    public String homepage() {
        return "home";
    }
}
