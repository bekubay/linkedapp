package edu.mum.linkedapp.controller;

import edu.mum.linkedapp.domain.User;
import edu.mum.linkedapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Binding;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/signup")
    public String signUp(@Valid User user, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "index";
        }
        if(userService.save(user)) {
            return "redirect:/success";
        }
        model.addAttribute("message","Password doesn't match");
        return "index";
    }
    @GetMapping("/success")
    public String signup_success(){
        return "signup_success";
    }
    @GetMapping("/followers")
    public String followers(){
        return "followers";
    }
    @GetMapping("/following")
    public String following(){
        return "following";
    }

    @GetMapping("/profile")
    public String showProfile(Model model){
        Optional<User> user = userService.findById(1L);
        System.out.println(user.get().getUsername());
        model.addAttribute("user", user.get());
        return "profile";
    }
}
