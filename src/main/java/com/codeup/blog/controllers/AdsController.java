package com.codeup.blog.controllers;

import com.codeup.blog.daos.AdsRepository;
import com.codeup.blog.daos.UsersRepository;
import com.codeup.blog.models.Ad;
import com.codeup.blog.models.User;
import com.codeup.blog.services.EmailService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdsController {

    // dependency injection
    private AdsRepository adsDao;
    private UsersRepository usersDao;
    private final EmailService emailService;

    public AdsController(AdsRepository adsRepository, UsersRepository usersRepository, EmailService emailService){
        this.adsDao = adsRepository;
        this.usersDao = usersRepository;
        this.emailService = emailService;
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
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        adToBeSaved.setOwner(currentUser);
        Ad savedAd = adsDao.save(adToBeSaved);
        emailService.prepareAndSend(savedAd, "A new ad has been creating", "An ad has been created with the id of " + savedAd.getId());
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
    public String destroy(@PathVariable long id){
        adsDao.deleteById(id);
        return "redirect:/ads";
    }

    @GetMapping("/search")
    public String searchResults(Model model, @RequestParam(name = "term") String term){
        List<Ad> ads = adsDao.searchByTitle(term);
        model.addAttribute("ads", ads);
        return "ads/index";
    }


}
