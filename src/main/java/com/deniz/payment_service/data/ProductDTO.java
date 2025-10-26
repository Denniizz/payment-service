package com.deniz.payment_service.data;

public class ProductDTO {

    private String name;
    private Long priceInCents;

    public ProductDTO(String name, Long priceInCents) {
        this.name = name;
        this.priceInCents = priceInCents;
    }

    public String getName() {
        return name;
    }

    public Long getPriceInCents() {
        return priceInCents;
    }

    // This is sent like displayPrice in Thymeleaf
    public String getDisplayPrice() {
        return String.format("%.2f", priceInCents / 100.0);
    }
}
