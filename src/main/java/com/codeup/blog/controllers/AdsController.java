package com.codeup.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdsController {

    @GetMapping("/ads/{id}")
    @ResponseBody
    public String show(@PathVariable long id){
        return "show ad id: " + id;
    }

    @PostMapping("/ads/create")
    @ResponseBody
    public String insert(){
        return "posted";
    }


}
