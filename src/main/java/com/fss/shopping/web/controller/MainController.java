package com.fss.shopping.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    @GetMapping("/")
    public String root() {
        LOGGER.info("Index request");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        LOGGER.info("Login request");
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
        LOGGER.info("user request");
        return "user/index";
    }
}
