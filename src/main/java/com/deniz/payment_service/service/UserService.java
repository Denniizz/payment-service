package com.deniz.payment_service.service;

import com.deniz.payment_service.entity.User;
import com.deniz.payment_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String email, String rawPassword)
    {
        if (userRepository.findByUsername(username).isPresent())
        {
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.findByEmail(email).isPresent())
        {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

}
