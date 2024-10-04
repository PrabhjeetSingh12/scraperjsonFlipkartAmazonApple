package com.example.urlscraper.scraper;

import com.example.urlscraper.scraper.dto.PriceComparisonResponse;
import com.example.urlscraper.scraper.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PriceComparisonService {

    private final AmazonScraper amazonScraper;
    private final AppleScraper appleScraper;

    public PriceComparisonService(AmazonScraper amazonScraper, AppleScraper appleScraper) {
        this.amazonScraper = amazonScraper;
        this.appleScraper = appleScraper;
    }

    public PriceComparisonResponse comparePrices(String amazonUrl, String appleUrl) throws IOException {
        ProductDto amazonProduct = amazonScraper.getPrice(amazonUrl);
        ProductDto appleProduct = appleScraper.getPrice(appleUrl);

        return new PriceComparisonResponse(amazonProduct, appleProduct);
    }
}
