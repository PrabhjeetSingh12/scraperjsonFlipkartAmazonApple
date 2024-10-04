package com.example.urlscraper.scraper.controller;

import com.example.urlscraper.scraper.AmazonScraper;
import com.example.urlscraper.scraper.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ScraperController {

    @Autowired
    private AmazonScraper amazonScraper;

    @GetMapping("/scrape")
    public ProductDto scrape(@RequestParam String url) {
        try {
            return amazonScraper.getPrice(url);
        } catch (IOException e) {
            System.err.println("Error fetching price: " + e.getMessage());
            return new ProductDto("Error", "Error fetching price");
        }
    }
}
