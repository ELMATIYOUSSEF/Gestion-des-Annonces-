package com.example.filerouge.security.auth;

import com.example.filerouge.domain.AppUser;
import com.example.filerouge.exception.UnauthorizedException;
import com.example.filerouge.web.dto.*;
import com.example.filerouge.web.mapper.AppUserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AppUserMapper appUserMapper;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest login) {
        JwtAuthenticationResponse result = authenticationService.login(login);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody @Valid RegisterRequest register) throws ValidationException, Exception {
        JwtAuthenticationResponse result = authenticationService.register(register);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/token/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(HttpServletRequest request) throws ValidationException, com.example.filerouge.utils.ValidationException {
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedException("Refresh token is missing");
        }
        String token = authorization.substring(7);
        JwtAuthenticationResponse result = authenticationService.refreshToken(token);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/auth")
    public ResponseEntity<UserResponseDto> auth() {
        AppUser result = authenticationService.auth();
        return ResponseEntity.ok(AppUserMapper.toDto(result));
    }

}
