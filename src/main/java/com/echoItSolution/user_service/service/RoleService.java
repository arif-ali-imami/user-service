package com.echoItSolution.user_service.service;

import com.echoItSolution.user_service.dto.AssignRoleRequestDTO;
import com.echoItSolution.user_service.dto.PermissionRequestDTO;
import com.echoItSolution.user_service.dto.RoleRequestDTO;
import com.echoItSolution.user_service.dto.UserDTO;
import com.echoItSolution.user_service.entity.Permission;
import com.echoItSolution.user_service.entity.Role;
import com.echoItSolution.user_service.entity.User;
import com.echoItSolution.user_service.enums.PermissionType;
import com.echoItSolution.user_service.enums.RoleType;
import com.echoItSolution.user_service.mapper.UserMapper;
import com.echoItSolution.user_service.repository.PermissionRepository;
import com.echoItSolution.user_service.repository.RoleRepository;
import com.echoItSolution.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public ResponseEntity<Role> create(RoleRequestDTO request) {
        Set<Permission> permissionSet = request.getPermissions().stream()
                .map(p -> permissionRepository.findByName(PermissionType.valueOf(p))
                        .orElseGet(() -> permissionRepository.save(new Permission(null, PermissionType.valueOf(p)))))
                .collect(Collectors.toSet());

        Role role = roleRepository.findByName(RoleType.valueOf(request.getRoleName()))
                .orElseGet(
                        () -> Role.builder().name(RoleType.valueOf(request.getRoleName()))
                                .permissions(new HashSet<>())
                                .build()
                );
        role.getPermissions().addAll(permissionSet);

        return ResponseEntity.ok(roleRepository.save(role));
    }

    public ResponseEntity<UserDTO> assignRoleToUser(AssignRoleRequestDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(RoleType.valueOf(request.getRoleName()))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getUserRoles().add(role);
        return ResponseEntity.ok(UserMapper.toDTO(userRepository.save(user)));
    }

    public ResponseEntity<Role> addPermissionToRole(PermissionRequestDTO requestDTO) {
        Role role = roleRepository.findById(requestDTO.getRoleId()).orElseThrow();

        Set<Permission> permissionSet = requestDTO.getPermissions().stream()
                .map(p -> permissionRepository.findByName(p)
                        .orElseGet(() -> permissionRepository.save(new Permission(null, PermissionType.valueOf(p)))))
                .collect(Collectors.toSet());

        role.getPermissions().addAll(permissionSet);
        return ResponseEntity.ok(roleRepository.save(role));
    }

    public ResponseEntity<UserDTO> unassignRoleToUser(AssignRoleRequestDTO request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(RoleType.valueOf(request.getRoleName()))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getUserRoles().remove(role);
        return ResponseEntity.ok(UserMapper.toDTO(userRepository.save(user)));

    }
}
