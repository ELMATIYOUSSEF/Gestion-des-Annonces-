package com.example.filerouge.service.impl;

import com.example.filerouge.domain.AppUser;
import com.example.filerouge.repository.UserRepository;
import com.example.filerouge.security.utils.SecurityUtils;
import com.example.filerouge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }

    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public AppUser getCurrentUser() {
        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if(currentUserLogin == null)
            throw new BadCredentialsException("No User Authenticated !!");
        return this.findByUsername(currentUserLogin);
    }
}
