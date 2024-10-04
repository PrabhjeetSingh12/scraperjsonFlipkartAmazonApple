package com.example.urlscraper.scraper;

import com.example.urlscraper.scraper.dto.PriceComparisonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PriceComparisonController {

    private final PriceComparisonService priceComparisonService;

    public PriceComparisonController(PriceComparisonService priceComparisonService) {
        this.priceComparisonService = priceComparisonService;
    }

    @GetMapping("/compare-prices")
    public PriceComparisonResponse comparePrices(@RequestParam String amazonUrl, @RequestParam String appleUrl) throws IOException {
        return priceComparisonService.comparePrices(amazonUrl, appleUrl);
    }
}
