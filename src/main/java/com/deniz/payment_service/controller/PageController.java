package com.deniz.payment_service.controller;

import com.deniz.payment_service.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PageController {

    private final ProductService productService;

    public PageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String landingPage()
    {
        return "landing"; // shows landing.html
    }

    @GetMapping("/products")
    public String productsPage(Model model, Principal principal)
    {
        model.addAttribute("username", principal.getName());
        model.addAttribute("products", productService.getProducts());
        return "products";
    }


}
