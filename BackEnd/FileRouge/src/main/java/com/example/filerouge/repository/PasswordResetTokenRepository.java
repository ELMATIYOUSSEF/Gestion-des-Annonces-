package com.example.filerouge.repository;

import com.example.filerouge.domain.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordReset, Long> {

    Optional<PasswordReset> findByToken(String token);
}
