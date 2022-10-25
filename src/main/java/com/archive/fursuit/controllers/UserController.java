package com.archive.fursuit.controllers;

import com.archive.fursuit.User;
import com.archive.fursuit.dto.UserRegistrationDto;
import com.archive.fursuit.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
        userService.saveUser(user);
        return new ModelAndView("redirect:/admin/registration?success");
    }
}
