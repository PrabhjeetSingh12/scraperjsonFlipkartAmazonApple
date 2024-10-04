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
public class AmazonScraper {

    @Autowired
    private ProductRepository productRepository; // Autowire the repository

    public ProductDto getPrice(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.5845.96 Safari/537.36")
                .get();

        // Select the specific price element
        Elements priceElements = doc.select("span.a-color-price"); // Selector for prices
        String mainPrice = "";

        // Iterate over the selected elements to find the main price
        for (var element : priceElements) {
            String text = element.text().trim();

            // Use regex to check for the main price format
            if (text.matches(".*\\d{1,3}(,\\d{3})*(\\.\\d{2})?") && !text.contains("₹5,949.00")) {
                mainPrice = text; // Store the main price
                break; // Exit after finding the main price
            }
        }

        // If main price is found, save the product data to the database
        if (!mainPrice.isEmpty()) {
            // Assuming you can get the product name from the document
            String productName = extractProductName(doc);
            Product product = new Product(productName, mainPrice);
            productRepository.save(product);
            return new ProductDto(productName, mainPrice); // Return as DTO
        }

        // Return null or an empty DTO if price not found
        return new ProductDto("Unknown Product", "Price not found");
    }

    private String extractProductName(Document doc) {
        // Example: Use a specific selector to get the product title from the page
        Elements titleElements = doc.select("span#productTitle"); // Adjust the selector as needed
        if (!titleElements.isEmpty()) {
            return titleElements.get(0).text().trim();
        }
        return "Unknown Product"; // Fallback if the title is not found
    }
}
