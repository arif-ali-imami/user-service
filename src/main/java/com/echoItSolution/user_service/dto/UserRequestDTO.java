package com.echoItSolution.user_service.dto;

import com.echoItSolution.user_service.enums.AuthProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequestDTO {

    private String userName;
    private String password;
    private String refreshToken;
    private String providerId;
    private AuthProviderType providerType;
}
