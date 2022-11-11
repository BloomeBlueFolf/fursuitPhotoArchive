package com.archive.fursuit.controllers;

import com.archive.fursuit.User;
import com.archive.fursuit.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger("UserController");

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/admin/registration")
    public String createUser(Model model){

        logger.info("User registration evoked. GET");

        User user = new User();
        model.addAttribute("user", user);
        logger.info("Registration form page evoked.");
        return "userregistration";
    }

    @PostMapping("/admin/registration")
    public ModelAndView createUser(@ModelAttribute ("user") User user){

        logger.info("User registration evoked. POST");

        if(userService.existsUser(user)) {
            logger.warn("User already exists. Cannot be created.");
            return new ModelAndView("redirect:/admin/registration?exists");
        } else {
            userService.saveUser(user);
            logger.info("User created. Redirection to registration page.");
            return new ModelAndView("redirect:/admin/registration?success");
        }
    }

    @GetMapping("/user/profile")
    public String showProfile(){

        logger.info("Accounts' profile evoked.");
        logger.info("Accounts' profile page evoked.");
        return "profile";
    }

    @GetMapping("/admin/accounts/show")
    public String showAccounts(Model model){

        List<User> accounts = new ArrayList<>();
        model.addAttribute("accounts", userService.findAllAccounts());
        logger.info("Account page evoked.");
        return "accounts";
    }

    @GetMapping("/admin/account/delete")
    public String deleteAccount(@RequestParam ("username") String username){

        logger.info("Account deletion evoked.");

        userService.deleteAccount(userService.findUser(username));
        logger.info(String.format("Account `%s` deleted. Redirection to account page.", username));
        return "redirect:/admin/accounts/show?success";
    }
}