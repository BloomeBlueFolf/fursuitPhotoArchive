package com.archive.fursuit.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);


    @GetMapping("/login")
    public String login(){

        logger.info("Login page evoked.");
        return "/login";
    }

    @GetMapping("/credits")
    public String credits(){

        logger.info("Credits page evoked.");
        return "/credits";
    }
}
