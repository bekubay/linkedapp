package edu.mum.linkedapp.controller;

import edu.mum.linkedapp.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/profile")
    public String showMyProfile(Model model, Principal principal){

        model.addAttribute("user", userService.findByUsername(principal.getName()).get());
        System.out.println("commit this");
        return "profile";
    }

    @GetMapping("/user/profile/{username}")
    public String showUserProfile(@PathVariable("username") String username, Model model, Principal principal){
        System.out.println(username);
        System.out.println("username");
        model.addAttribute("user", userService.findByUsername(username).get());
        return "profile";
    }

}
