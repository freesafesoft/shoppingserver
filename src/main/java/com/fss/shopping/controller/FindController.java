package com.fss.shopping.controller;

import com.fss.shopping.entity.Market;
import com.fss.shopping.repository.MarketRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/find")
public class FindController {
    final MarketRepository visitsRepository;

    public FindController(MarketRepository visitsRepository) {
        this.visitsRepository = visitsRepository;
    }

    @GetMapping("/visits")
    public Iterable<Market> getVisits() {
        return visitsRepository.findAll();
    }
}
