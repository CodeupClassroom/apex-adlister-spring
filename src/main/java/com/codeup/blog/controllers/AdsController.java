package com.codeup.blog.controllers;

import com.codeup.blog.daos.AdsRepository;
import com.codeup.blog.models.Ad;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdsController {

    // dependency injection
    private AdsRepository adsDao;
    public AdsController(AdsRepository adsRepository){
        adsDao = adsRepository;
    }

    @GetMapping("/ads")
//    @RequestMapping(value = "/ads", method = RequestMethod.GET)
    public String index(Model model){
        List<Ad> adsList = adsDao.findAll();
        model.addAttribute("noAdsFound", adsList.size() == 0);
        model.addAttribute("ads", adsList);
        return "ads/index";
    }

    @GetMapping("/ads/{id}")
    public String show(@PathVariable long id, Model model){
        model.addAttribute("adId", id);
        model.addAttribute("ad", new Ad("PS1", "Used"));
        return "/ads/show";
    }

    @GetMapping("/ads/create")
    @ResponseBody
    public String showForm(){
        return "view the form for creating an ad";
    }

    @PostMapping("/ads/create")
    @ResponseBody
    public String save()
    {
        Ad newAd = new Ad("XBOX X","brand new");
        adsDao.save(newAd);
        return "create a new ad";
    }

    @PutMapping("/ads/{id}/edit")
    @ResponseBody
    public String update(@PathVariable long id){
        // find an ad
        Ad foundAd = adsDao.getOne(id); // select * from ads where id = ?
        // edit the ad
        foundAd.setTitle("XBOX Series X");
        foundAd.setDescription("holiday");
        // save the changes
        adsDao.save(foundAd); // update ads set title = ? where id = ?
        return "ad updated";
    }

    @DeleteMapping("/ads/{id}/delete")
    @ResponseBody
    public String destroy(@PathVariable long id){
        adsDao.deleteById(id);
        return "ad deleted";
    }


}
