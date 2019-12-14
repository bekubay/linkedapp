package edu.mum.linkedapp.controller;

import edu.mum.linkedapp.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/"})
    public String index(Model model){
        model.addAttribute("user", new User());
        return "index";
    }
    @GetMapping({"/user/home"})
    public String home(Model model){
        model.addAttribute("user", new User());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal());
        model.addAttribute("principal", auth.getPrincipal());
        return "user_timeline";
    }
}
