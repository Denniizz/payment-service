package com.deniz.payment_service.controller;

import com.deniz.payment_service.data.ProductRequest;
import com.deniz.payment_service.data.StripeResponse;
import com.deniz.payment_service.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductCheckoutController {

    private StripeService stripeService;

    public ProductCheckoutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkoutProducts(@ModelAttribute ProductRequest productRequest)
    {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", stripeResponse.getSessionUrl())
                .build();
    }

}
