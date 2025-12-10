package com.tech.padawan.academy.user.dto;

import com.tech.padawan.academy.role.model.Role;

import java.util.List;

public record RecoveryUserDTO(
        Long id,
        String email,
        List<Role> roles
) {
}
