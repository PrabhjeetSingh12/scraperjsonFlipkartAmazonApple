package com.example.urlscraper.scraper;

import com.example.urlscraper.scraper.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        Product product = productService.getProductByName(name);
        return ResponseEntity.ok(product);
    }

    // Add this method to retrieve all products
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{name}/prices")
    public ResponseEntity<List<String>> getProductPrices(@PathVariable String name, @RequestParam(defaultValue = "10") int limit) {
        List<String> prices = productService.getFormattedPrices(name,limit);
        return ResponseEntity.ok(prices);
    }

    @GetMapping("/{name}/prices-with-date")
    public ResponseEntity<Map<String, LocalDate>> getProductPricesWithDate(
            @PathVariable String name,
            @RequestParam(defaultValue = "10") int limit) {
        Map<String, LocalDate> pricesWithDate = productService.getFormattedPricesWithDate(name, limit);
        return ResponseEntity.ok(pricesWithDate);
    }

    @GetMapping("/compare-prices")
    public ResponseEntity<Map<String, String>> getComparePrices() {
        String greenProductName = "iPhone 15 128GB Green"; // Apple product
        String blackProductName = "Apple iPhone 15 (128 GB) - Black"; // Amazon product

        Map<String, String> prices = productService.getBothPricesWithDates(greenProductName, blackProductName);
        return ResponseEntity.ok(prices);
    }




}


