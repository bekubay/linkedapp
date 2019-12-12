package edu.mum.linkedapp.controller;

import edu.mum.linkedapp.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/","/login"})
    public String index(Model model){
        model.addAttribute("user", new User());
        return "index";
    }
    @GetMapping({"/home"})
    public String home(Model model){
        model.addAttribute("user", new User());
        return "user_timeline";
    }
}
