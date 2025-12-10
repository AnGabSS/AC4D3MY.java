package com.tech.padawan.academy.user.dto;

import com.tech.padawan.academy.role.model.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateUserDTO(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Nickname is required")
        String nickname,
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Department is required")
        String department,
        @NotBlank(message = "Password is required")
        String password,
        @NotNull(message = "Birthdate is required")
        LocalDate birthdate,
        @NotNull(message = "Role is required")
        RoleType role
) {
}
