package com.archive.fursuit.controllers;

import com.archive.fursuit.User;
import com.archive.fursuit.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/admin/registration")
    public String createUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "userregistration";
    }

    @PostMapping("/admin/registration")
    public ModelAndView createUser(@ModelAttribute ("user") User user){
        if(userService.existsUser(user)) {
            return new ModelAndView("redirect:/admin/registration?exists");
        }
        else {
            userService.saveUser(user);
            return new ModelAndView("redirect:/admin/registration?success");
        }
    }

    @GetMapping("/user/profile")
    public String showProfile(){
        return "profile";
    }

    @GetMapping("/admin/accounts/show")
    public String showAccounts(Model model){
        List<User> accounts = new ArrayList<>();
        model.addAttribute("accounts", userService.findAllAccounts());
        return "accounts";
    }

    @GetMapping("/admin/account/delete")
    public String deleteAccount(@RequestParam ("username") String username){
        userService.deleteAccount(userService.findUser(username));
        return "redirect:/admin/accounts/show?success";
    }
}

//create user
//show all users
//delete user
