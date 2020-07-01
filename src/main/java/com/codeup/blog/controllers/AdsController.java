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
    public String showForm(Model viewModel){
        viewModel.addAttribute("ad", new Ad());
        return "ads/create";
    }

    @PostMapping("/ads/create")
    public String save(@ModelAttribute Ad adToBeSaved) {
        User currentUser = usersDao.getOne(1L);
        adToBeSaved.setOwner(currentUser);
        Ad savedAd = adsDao.save(adToBeSaved);
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
    public String update(@ModelAttribute Ad adtoEdit){
        User currentUser = usersDao.getOne(1L);
        adtoEdit.setOwner(currentUser);
        // save the changes
        adsDao.save(adtoEdit); // update ads set title = ? where id = ?
        return "redirect:/ads/" + adtoEdit.getId();
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
