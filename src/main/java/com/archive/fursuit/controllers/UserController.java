package com.archive.fursuit.controllers;

import com.archive.fursuit.dto.UserRegistrationDto;
import com.archive.fursuit.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @GetMapping("/user/registration")
    public String createUser(Model model){
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute("userRegistrationDto", userRegistrationDto);
        return "userregistration";
    }

    @PostMapping("/user/registration")
    public ModelAndView createUser(@ModelAttribute ("userRegistrationDto") UserRegistrationDto userRegistrationDto){
        userService.saveUser(userRegistrationDto);
        return new ModelAndView("redirect:/user/registration?success");
    }
}
