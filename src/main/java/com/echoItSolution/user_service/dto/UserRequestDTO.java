package com.echoItSolution.user_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDTO {

    private String userName;
    private String password;
    private String refreshToken;
}
