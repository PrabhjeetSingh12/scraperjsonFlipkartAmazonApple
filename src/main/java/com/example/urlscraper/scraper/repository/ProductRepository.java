package com.example.urlscraper.scraper.repository;

import com.example.urlscraper.scraper.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
