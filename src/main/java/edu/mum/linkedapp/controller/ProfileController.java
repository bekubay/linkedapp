package edu.mum.linkedapp.controller;

import edu.mum.linkedapp.domain.User;
import edu.mum.linkedapp.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.security.Principal;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/profile")
    public String showMyProfile(Model model, Principal principal){

        User user = userService.findByUsername(principal.getName()).get();
        try {
            model.addAttribute("profilepath", user.getProfile().getProfile_pic_url());
        }catch(Exception ex){
            System.out.println("no profile picture");
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/user/profile/{username}")
    public String showUserProfile(@PathVariable("username") String username, Model model, Principal principal){
        User user = userService.findByUsername(username).get();
        try {
            model.addAttribute("profilepath", new File("").getAbsolutePath() + "/upload-dir/" + user.getProfile().getProfile_pic_url());
        }catch(Exception ex){
            System.out.println("no profile picture");
        }
        model.addAttribute("user", user);
        return "profile";
    }

}
