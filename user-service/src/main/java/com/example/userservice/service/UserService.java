package com.example.userservice.service;

import com.example.userservice.entities.UserEntity;
import com.example.userservice.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthTokenService authTokenService;

//    public UserDetails loadUserByUsername(String username) {
//        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
//        if(userEntity.) {
//            throw new RuntimeException("User Not Found");
//        }
//
//        return User.withUsername(userEntity.getUsername())
//                .password(userEntity.getPassword())
//                .roles("USER")
//                .build();
//    }

    public String saveUser(UserEntity userEntity) {
        if(userRepository.existsByUsername(userEntity.getUsername())) {
            throw new RuntimeException("Username already exist");
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        return "User Successfully added";
    }

    public String generateToken(String username) {
        return authTokenService.generateToken(username);
    }

    public void validateToken(String token) {
        authTokenService.validateToken(token);
    }
}
