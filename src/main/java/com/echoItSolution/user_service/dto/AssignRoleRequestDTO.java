package com.echoItSolution.user_service.dto;

import lombok.Data;

@Data
public class AssignRoleRequestDTO {
    private Long userId;
    private String roleName;
}
