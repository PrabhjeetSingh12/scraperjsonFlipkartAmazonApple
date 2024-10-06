package com.example.urlscraper.scraper;

import com.example.urlscraper.scraper.dto.ProductDto;
import com.example.urlscraper.scraper.model.Product;
import com.example.urlscraper.scraper.repository.ProductRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppleScraper {

    @Autowired
    private ProductRepository productRepository; // Autowire the repository

    public ProductDto getPrice(String url) {
        try {
            // Set up the connection with User-Agent and timeout
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.5845.96 Safari/537.36")
                    .timeout(10 * 1000) // Timeout of 10 seconds
                    .get();

            // Select the JSON-LD script tag
            Elements jsonLdElements = doc.select("script[type='application/ld+json']");

            for (var jsonLdElement : jsonLdElements) {
                String jsonData = jsonLdElement.html();
                String price = extractPriceFromJson(jsonData);
                if (price != null) {
                    // Save the product to the database
                    Product product = new Product("iPhone 15 128GB Green", price);
                    productRepository.save(product);
                    return new ProductDto(product.getName(), price); // Return ProductDto
                }
            }
            return new ProductDto("Unknown Product", "Price not found");
        } catch (IOException e) {
            System.err.println("Error fetching price: " + e.getMessage());
            return new ProductDto("Error", "Error fetching price");
        }
    }

    private String extractPriceFromJson(String jsonData) {
        try {
            var jsonObject = new org.json.JSONObject(jsonData);
            var offers = jsonObject.getJSONArray("offers");
            var price = offers.getJSONObject(0).getDouble("price");
            return String.valueOf(price); // Return raw price as a string without currency formatting
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return null;
        }
    }
}

