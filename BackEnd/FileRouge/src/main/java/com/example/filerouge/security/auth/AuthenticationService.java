package com.example.filerouge.security.auth;

import com.example.filerouge.domain.AppRole;
import com.example.filerouge.domain.AppUser;
import com.example.filerouge.domain.Enums.Role;
import com.example.filerouge.exception.UnauthorizedException;
import com.example.filerouge.repository.RoleRepository;
import com.example.filerouge.repository.UserRepository;
import com.example.filerouge.security.jwt.JwtService;
import com.example.filerouge.security.jwt.TokenType;
import com.example.filerouge.service.UserService;
import com.example.filerouge.utils.CustomError;
import com.example.filerouge.utils.ValidationException;
import com.example.filerouge.web.dto.LoginRequest;
import com.example.filerouge.web.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserService userService ;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder ;
    private final JwtService jwtService ;
    private final AuthenticationManager authenticationManager;
    public JwtAuthenticationResponse login(LoginRequest login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword())
        );
        var user = userRepository.findByEmail(login.getEmail()).orElseThrow(()-> new UsernameNotFoundException("No User with this Email !!"));
        String accessToken = jwtService.generateToken(user, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(user, TokenType.REFRESH_TOKEN);
        return JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtAuthenticationResponse register(RegisterRequest registerRequest) throws Exception {
        Optional<AppUser> appUser = userRepository.findByEmail(registerRequest.getEmail());
        if(appUser.isPresent()) throw new Exception("this Email is Already Exist ") ;
        AppRole name = roleRepository.findByName(Role.ROLE_CLIENT.name()).orElseThrow(()-> new UsernameNotFoundException("no Role with this name"));

        AppUser user = AppUser.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(List.of(name))
                .build();
        userRepository.save(user);
        String accessToken = jwtService.generateToken(user, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(user, TokenType.REFRESH_TOKEN);
        return JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    public JwtAuthenticationResponse refreshToken(String  refreshToken) throws ValidationException {
        if(jwtService.isTokenValid(refreshToken, TokenType.REFRESH_TOKEN)) {
            String username = jwtService.extractUserName(refreshToken);
            var user = userRepository.findByEmail(username).orElseThrow(() -> new ValidationException(new CustomError("email", "User not found")));
            var accessToken = jwtService.generateToken(user, TokenType.ACCESS_TOKEN);
            return JwtAuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        throw new UnauthorizedException("Refresh token is invalid");
    }

    public AppUser auth() {
        return userService.getCurrentUser();
    }
}
