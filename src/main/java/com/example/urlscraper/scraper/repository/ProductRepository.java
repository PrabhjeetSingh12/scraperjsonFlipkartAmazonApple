package com.example.urlscraper.scraper.repository;

import com.example.urlscraper.scraper.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE UPPER(p.name) = UPPER(:name) ORDER BY p.createdDate DESC")
    List<Product> findByNameIgnoreCase(@Param("name")String name);

    @Query("SELECT p.price FROM Product p WHERE LOWER(p.name) = LOWER(:name) ORDER BY p.createdDate DESC")
    List<String> findPricesByName(@Param("name")String name);




}

