package com.example.userservice.service;

import com.example.userservice.config.CustomUserDetails;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entities.UserEntity;
import com.example.userservice.exceptions.AppException;
import com.example.userservice.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
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

    public String saveUser(UserDto userDto, String role) throws AppException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        if(userRepository.existsByUsername(userEntity.getUsername())) {
            throw new AppException("Username already exist", HttpStatus.BAD_REQUEST);
        }
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setRole(role);
        userRepository.save(userEntity);
        return "User Successfully added";
    }

    public String generateToken(CustomUserDetails userDetails) {
        return authTokenService.generateToken(userDetails);
    }

    public void validateToken(String token) {
        authTokenService.validateToken(token);
    }

    public Map<String, Object> getData(String token) {
        return authTokenService.getData(token);
    }
}
