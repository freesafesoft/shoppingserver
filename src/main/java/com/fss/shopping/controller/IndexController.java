package com.fss.shopping.controller;

import com.fss.shopping.repository.MarketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    final MarketRepository marketRepository;

    public IndexController(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index", "name", "Alex");
    }
}