package com.echoItSolution.user_service.repository;

import com.echoItSolution.user_service.entity.Permission;
import com.echoItSolution.user_service.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);

    Optional<Permission> findByName(PermissionType permission);

}