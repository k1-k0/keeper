package com.electricity.keeper.repository;

import com.electricity.keeper.model.ERole;
import com.electricity.keeper.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
