package com.example.userservice.controllers;

import com.example.userservice.AuthRequest;
import com.example.userservice.entities.UserEntity;
import com.example.userservice.repos.UserRepository;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addUser(@RequestBody UserEntity user) {
        return userService.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            return userService.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("Invalid username and password");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        System.out.println("sessiiiiii");
        userService.validateToken(token);
        return "user validated";
    }

}
