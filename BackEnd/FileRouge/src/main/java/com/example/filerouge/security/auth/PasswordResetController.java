package com.example.filerouge.security.auth;

import com.example.filerouge.domain.AppUser;
import com.example.filerouge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetTokenService tokenService;
    private final UserService userService;

    @PostMapping("/request")
    public ResponseEntity<Void> requestPasswordReset(@RequestParam String email) {
        AppUser user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        tokenService.createToken(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        return ResponseEntity.ok(tokenService.validateToken(token));
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        if (!tokenService.validateToken(token))
            throw new UsernameNotFoundException("Token invalide");

        tokenService.resetPassword(token, newPassword);
        return ResponseEntity.ok().build();
    }
}
