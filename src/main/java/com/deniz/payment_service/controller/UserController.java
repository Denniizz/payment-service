package com.deniz.payment_service.controller;

import com.deniz.payment_service.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // The params come from the form into the method
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model)
    {
        // Check password match
        if (!password.equals(confirmPassword))
        {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }

        try {
            userService.registerUser(username, email, password);
        }
        catch (RuntimeException e)
        {
            model.addAttribute("error", e.getMessage());
            return "register";
        }

        // Registration successful → redirect to login
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(){
        return "register";
    }


}
