package edu.mum.linkedapp.controller;

import edu.mum.linkedapp.domain.User;
import edu.mum.linkedapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Binding;
import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;
    @GetMapping(value = "/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,  @RequestParam(value = "logout", required = false) String logout,  Model model) {
        String errorMessge = null;
        if (error != null) {
            errorMessge = "Username or Password is incorrect !!";
            model.addAttribute("errorMessge", errorMessge);
        }
        if (logout != null) {
            errorMessge = "You have been successfully logged out !!";
            model.addAttribute("errorMessge", errorMessge);
        }
        model.addAttribute("user", new User());
        return "index";
    }


    @GetMapping("/denied")
    public String accessDenied(){
        return "accessDenied";
    }
    @PostMapping("/signup")
    public String signUp(@Valid User user, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "index";
        }
        if(userService.save(user)) {
            return "redirect:/user/success";
        }
        model.addAttribute("message","Password doesn't match");
        return "index";
    }
    @GetMapping("/user/success")
    public String signup_success(){
        return "signup_success";
    }
    
    @GetMapping("/user/followers")
    public String followers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "followers";
    }

    @GetMapping("/user/following")
    public String following(Model model){
        model.addAttribute("users", userService.findAll());
        return "following";
    }

    @GetMapping("/admin/users")
    public String usersAll(Model model){
        model.addAttribute("users", userService.findAll());
        return "users";
    }

     @GetMapping("/user/profile")
    public String showProfile(Model model, Principal principal){

        model.addAttribute("user", userService.findByUsername(principal.getName()).get());
        System.out.println("commit this");
        return "profile";
    }


}
