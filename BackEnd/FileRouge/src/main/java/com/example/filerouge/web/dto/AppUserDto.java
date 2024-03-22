package com.example.filerouge.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.example.filerouge.domain.AppUser}
 *
 * @param password @Min(value = 8,message = "Password should be at least 8 characters")
 */
public record AppUserDto(
                         Long id,
                         @NotBlank(message = "First name cannot be blank")
                         String firstName,
                         @NotBlank(message = "Last name cannot be blank")
                         String lastName,
                         @NotBlank(message = "Email cannot be blank")
                         @Email(message = "Email should be valid")
                         String email,
                         @NotBlank(message = "Password cannot be blank")
                         String password,
                         List<String> authorities) implements Serializable {

}
