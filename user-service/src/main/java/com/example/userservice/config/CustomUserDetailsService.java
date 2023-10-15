package com.example.userservice.config;

import com.example.userservice.entities.UserEntity;
import com.example.userservice.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        return userEntity.map(CustomeUserDetails::new).orElseThrow(() -> new RuntimeException("USername not valid"));
    }
}
