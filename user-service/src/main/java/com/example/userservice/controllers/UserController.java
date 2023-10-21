package com.example.userservice.controllers;

import com.example.userservice.AuthRequest;
import com.example.userservice.config.CustomUserDetails;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entities.UserEntity;
import com.example.userservice.exceptions.AppException;
import com.example.userservice.repos.UserRepository;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addUser(@RequestBody UserDto user) throws AppException {
        return userService.saveUser(user, "USER");
    }

    @PostMapping("/admin/register")
    public String addAdmin(@RequestBody UserDto user) throws AppException {
        return userService.saveUser(user, "ADMIN");
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) throws AppException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            return userService.generateToken((CustomUserDetails) authentication.getPrincipal());
        } else {
            throw new AppException("Invalid username and password", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/data")
    public Map<String, Object> getData(@RequestParam("token") String token) {
        return userService.getData(token);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        System.out.println("sessiiiiii");
        userService.validateToken(token);
        return "user validated";
    }

}
