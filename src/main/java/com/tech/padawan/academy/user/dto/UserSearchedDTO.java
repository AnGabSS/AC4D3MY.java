package com.tech.padawan.academy.user.dto;

import com.tech.padawan.academy.role.model.RoleType;
import com.tech.padawan.academy.user.model.User;

import java.time.LocalDate;

public record UserSearchedDTO(
        Long id,
        String name,
        String email,
        String department,
        LocalDate birthdate,
        RoleType role
) {
    public static UserSearchedDTO from(User user) {
        return new UserSearchedDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getDepartment(),
                user.getBirthdate(),
                user.getLastAddRole()
        );
    }
}