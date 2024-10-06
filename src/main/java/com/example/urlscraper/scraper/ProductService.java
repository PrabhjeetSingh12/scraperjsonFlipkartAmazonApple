package com.example.urlscraper.scraper;

import com.example.urlscraper.scraper.model.Product;
import com.example.urlscraper.scraper.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public long countProducts() {
        return productRepository.count();
    }

    public List<Product> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        System.out.println("All Products: " + allProducts);
        return allProducts;
    }


    public Product getProductByName(String name) {
        List<Product> products = productRepository.findByNameIgnoreCase(name);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with name: " + name);
        }
        // Assuming your Product class has a createdDate field
        return products.stream()
                .filter(product -> product.getCreatedDate() != null) // Ensure createdDate is not null

                .max(Comparator.comparing(Product::getCreatedDate))
                .orElseThrow(() -> new ResourceNotFoundException("No products found with name: " + name));
    }


    public List<String> getFormattedPrices(String name, int limit) {
        List<String> prices = productRepository.findPricesByName(name);

        return prices.stream()
                .limit(limit)
                .map(price -> price.replace("₹", ""))  // Remove the rupee symbol from strings
                .collect(Collectors.toList());
    }


    public Map<String, LocalDate> getFormattedPricesWithDate(String name, int limit) {
        List<Product> products = productRepository.findByNameIgnoreCase(name);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product not found with name: " + name);
        }

        return products.stream()
                .limit(limit)
                .collect(Collectors.toMap(
                        product -> product.getPrice().replace("₹", ""), // Replace with the actual price fetching method
                        product -> product.getCreatedDate().toLocalDate(),
                        (existing, replacement) -> replacement// Fetch the creation date
                ));
    }

    public Map<String, String> getBothPricesWithDates(String greenProductName, String blackProductName) {
        // Fetch products by name
        List<Product> greenProducts = productRepository.findByNameIgnoreCase(greenProductName);
        List<Product> blackProducts = productRepository.findByNameIgnoreCase(blackProductName);

        // Handle cases where products are not found
        if (greenProducts.isEmpty() || blackProducts.isEmpty()) {
            throw new ResourceNotFoundException("One or both products not found");
        }

        // Fetch the latest product for each
        Optional<Product> latestGreenProduct = greenProducts.stream()
                .filter(product -> product.getCreatedDate() != null) // Ensure createdDate is not null
                .max(Comparator.comparing(Product::getCreatedDate));

        Optional<Product> latestBlackProduct = blackProducts.stream()
                .filter(product -> product.getCreatedDate() != null) // Ensure createdDate is not null
                .max(Comparator.comparing(Product::getCreatedDate));

        // Prepare the response map
        Map<String, String> pricesWithDates = new HashMap<>();

        // Add green product details
        latestGreenProduct.ifPresent(product ->
                pricesWithDates.put("Apple iPhone 15 (128GB) Black (Apple)",
                        "Price: " + product.getPrice() + ", Date: " + product.getCreatedDate().toLocalDate())
        );

        // Add black product details
        latestBlackProduct.ifPresent(product ->
                pricesWithDates.put("Apple iPhone 15 (128 GB) Black (Amazon)",
                        "Price: " + product.getPrice() + ", Date: " + product.getCreatedDate().toLocalDate())
        );

        // Handle case where products with valid created dates were not found
        if (pricesWithDates.isEmpty()) {
            throw new ResourceNotFoundException("No valid products with created dates found.");
        }

        return pricesWithDates;
    }


}













