package edu.mum.linkedapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHome(Model model){
        model.addAttribute("msg", "welcom home!!");
        return "index";
    }
}
