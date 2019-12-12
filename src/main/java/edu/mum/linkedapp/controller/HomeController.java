package edu.mum.linkedapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/index_user")
    public String indexUser(){
        return "index_user";
    }
}
