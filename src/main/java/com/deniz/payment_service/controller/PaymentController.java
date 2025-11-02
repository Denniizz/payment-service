package com.deniz.payment_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

    @GetMapping("/success")
    public String paymentSuccess() {
        return "dir/success"; // include subdirectory
    }

    @GetMapping("/cancel")
    public String paymentCancel() {
        return "dir/cancel"; // include subdirectory
    }
}
