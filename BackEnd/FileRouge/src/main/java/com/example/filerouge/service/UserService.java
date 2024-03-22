package com.example.filerouge.service;

import com.example.filerouge.domain.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    UserDetailsService userDetailsService();
    AppUser getCurrentUser();
    AppUser findByUsername(String username);

    Optional<AppUser> findByEmail(String email);
}
