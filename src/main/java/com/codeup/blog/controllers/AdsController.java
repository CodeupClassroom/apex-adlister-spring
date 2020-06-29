package com.codeup.blog.controllers;

import com.codeup.blog.daos.AdsRepository;
import com.codeup.blog.daos.UsersRepository;
import com.codeup.blog.models.Ad;
import com.codeup.blog.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdsController {

    // dependency injection
    private AdsRepository adsDao;
    private UsersRepository usersDao;

    public AdsController(AdsRepository adsRepository, UsersRepository usersRepository){
        adsDao = adsRepository;
        usersDao = usersRepository;
    }

    @GetMapping("/ads")
//    @RequestMapping(value = "/ads", method = RequestMethod.GET)
    public String index(Model model){
        
        Ad firstAd = adsDao.findFirstByTitle("psvita");
        System.out.println("firstAd.getId() = " + firstAd.getId());
        
        List<Ad> adsList = adsDao.findAll();
        model.addAttribute("noAdsFound", adsList.size() == 0);
        model.addAttribute("ads", adsList);
        return "ads/index";
    }

    @GetMapping("/ads/{id}")
    public String show(@PathVariable long id, Model model){
        Ad ad = adsDao.getOne(id);
        model.addAttribute("adId", id);
        model.addAttribute("ad", ad);
        return "/ads/show";
    }

    @GetMapping("/ads/create")
    public String showForm(){
        return "ads/create";
    }

    @PostMapping("/ads/create")
    public String save(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "description") String description
    )
    {
        User currentUser = usersDao.getOne(1L);
        Ad newAd = new Ad(title, description, currentUser, null, null);
        Ad savedAd = adsDao.save(newAd);
        return "redirect:/ads/" + savedAd.getId();
    }

    @GetMapping("/ads/{id}/edit")
    public String showEditForm(Model model, @PathVariable long id){
        // find an ad
        Ad adToEdit = adsDao.getOne(id);
        model.addAttribute("ad", adToEdit);
        return "ads/edit";
    }

    @PostMapping("/ads/{id}/edit")
    @ResponseBody
    public String update(@PathVariable long id,
                         @RequestParam(name = "title") String title,
                         @RequestParam(name = "description") String desc){
        // find an ad
        Ad foundAd = adsDao.getOne(id); // select * from ads where id = ?
        // edit the ad
        foundAd.setTitle(title);
        foundAd.setDescription(desc);
        // save the changes
        adsDao.save(foundAd); // update ads set title = ? where id = ?
        return "ad updated";
    }

    @PostMapping("/ads/{id}/delete")
    @ResponseBody
    public String destroy(@PathVariable long id){
        adsDao.deleteById(id);
        return "ad deleted";
    }

    @GetMapping("/search")
    public String searchResults(Model model, @RequestParam(name = "term") String term){
        List<Ad> ads = adsDao.searchByTitle(term);
        model.addAttribute("ads", ads);
        return "ads/index";
    }


}
