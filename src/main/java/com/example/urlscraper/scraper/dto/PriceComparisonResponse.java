package com.example.urlscraper.scraper.dto;

public class PriceComparisonResponse {
    private ProductDto amazonProduct;
    private ProductDto appleProduct;

    public PriceComparisonResponse(ProductDto amazonProduct, ProductDto appleProduct) {
        this.amazonProduct = amazonProduct;
        this.appleProduct = appleProduct;
    }

    public ProductDto getAmazonProduct() {
        return amazonProduct;
    }

    public ProductDto getAppleProduct() {
        return appleProduct;
    }
}
