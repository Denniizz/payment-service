package com.deniz.payment_service.service;

import com.deniz.payment_service.data.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public List<ProductDTO> getProducts()
    {
        return List.of(
                new ProductDTO("Tablet", 15000L),
                new ProductDTO("Headphones", 5000L),
                new ProductDTO("Laptop", 75000L),
                new ProductDTO("Mouse", 1700L),
                new ProductDTO("Keyboard", 4900L),
                new ProductDTO("Gaming chair", 10000L)
        );
    }

}
