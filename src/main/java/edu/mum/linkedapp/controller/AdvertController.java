package edu.mum.linkedapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdvertController {
    @GetMapping("/advert/adverts")
    public String advertsAdminPage(){
        return "adverts_admin";
    }
}
