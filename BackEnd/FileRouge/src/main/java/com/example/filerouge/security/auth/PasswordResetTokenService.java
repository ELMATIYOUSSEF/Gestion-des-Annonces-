package com.example.filerouge.security.auth;

import com.example.filerouge.domain.AppUser;
import com.example.filerouge.domain.PasswordReset;
import com.example.filerouge.repository.PasswordResetTokenRepository;
import com.example.filerouge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    public void createToken(AppUser user) {
        String token = UUID.randomUUID().toString();
        PasswordReset resetToken = PasswordReset.builder()
                .user(user)
                .token(token)
                .expiryDate(LocalDateTime.now().plusMinutes(10))
                .build();
        PasswordReset passwordReset = tokenRepository.save(resetToken);

        sendPasswordResetEmail(user.getEmail(), passwordReset.getToken());
    }

    private void sendPasswordResetEmail(String recipientEmail, String token) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(recipientEmail);
            helper.setSubject("Password Reset Off Your Account");
            // HTML content for the email body
            String htmlContent = "<html><body style='font-family: Arial, sans-serif;'>"
                    + "<h2 style='color: #d4dbf9; text-align: center;'>Reset Your Password</h2>"
                    + "<p style='text-align: center;'>To reset your password, click the button below:</p>"
                    + "<div style='text-align: center; margin: 20px;'>"
                    + "<a href='http://localhost:4200/account/reset-password?token=" + token + "' style='"
                    + "background-color: #d4dbf9; color: #ffffff; text-decoration: none; padding: 15px 30px; border-radius: 8px; font-weight: bold; display: inline-block;'>"
                    + "Reset Password</a></div>"
                    + "</body></html>";


            helper.setText(htmlContent, true); // Set to true for HTML content
            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle exception
            e.printStackTrace();
            throw new RuntimeException("Error has been occurred while sending the reset password contact support!");
        }
    }

    public boolean validateToken(String token) {
        PasswordReset resetToken = tokenRepository.findByToken(token).orElse(null);
        return resetToken != null && !resetToken.isExpired();
    }

    public void resetPassword(String token, String newPassword) {
        PasswordReset resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new UsernameNotFoundException("Token invalide"));
        AppUser user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
