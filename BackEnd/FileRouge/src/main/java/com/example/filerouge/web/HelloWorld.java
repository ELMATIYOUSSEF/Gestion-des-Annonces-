package com.example.filerouge.web;

import com.example.filerouge.exception.UnauthorizedException;
import com.example.filerouge.security.auth.AuthenticationService;
import com.example.filerouge.security.auth.JwtAuthenticationResponse;
import com.example.filerouge.security.jwt.JwtServiceImpl;
import com.example.filerouge.web.dto.RegisterRequest;
import com.example.filerouge.web.dto.permissionRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class HelloWorld {

    private final AuthenticationService authenticationService;
    private final JwtServiceImpl jwtService ;

    @GetMapping
    public String hello() { return "Hello world"; }

    @PostMapping("/assign")
    @PreAuthorize("hasAuthority('SCOPE_ASSIGNTOOTHER')")
    public Collection<? extends GrantedAuthority>   assignPermissionToRole(@RequestBody permissionRequest permissionRequest , HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedException("token is missing");
        }
        String token = authorization.substring(7);
        return jwtService.extractAuthorities(token);
    }
}
