package com.codeup.blog.controllers;

import com.codeup.blog.models.Ad;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdsController {

    @GetMapping("/ads")
//    @RequestMapping(value = "/ads", method = RequestMethod.GET)
    public String index(Model model){
        ArrayList<Ad> adsList = new ArrayList<>();
        adsList.add(new Ad("PS1", "Used"));
        adsList.add(new Ad("PS2", "Used"));
        adsList.add(new Ad("PS4", "Used"));
        adsList.add(new Ad("SNES", "Used"));
        model.addAttribute("noAdsFound", adsList.size() == 0);
        model.addAttribute("ads", adsList);
        return "ads/index";
    }

    @GetMapping("/ads/{id}")
    @ResponseBody
    public String show(@PathVariable long id){
        return "view an individual ad with the id of: " + id;
    }

    @GetMapping("/ads/create")
    @ResponseBody
    public String showForm(){
        return "view the form for creating an ad";
    }

    @PostMapping("/ads/create")
    @ResponseBody
    public String save(){
        return "create a new ad";
    }


}
