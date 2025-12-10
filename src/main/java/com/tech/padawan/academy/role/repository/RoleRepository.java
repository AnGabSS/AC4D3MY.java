package com.tech.padawan.academy.role.repository;

import com.tech.padawan.academy.role.model.Role;
import com.tech.padawan.academy.role.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
