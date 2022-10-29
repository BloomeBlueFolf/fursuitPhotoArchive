package com.archive.fursuit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/credits")
    public String credits(){
        return "/credits";
    }
}
