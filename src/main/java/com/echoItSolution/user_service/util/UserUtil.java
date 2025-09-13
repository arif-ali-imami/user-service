package com.echoItSolution.user_service.util;

import com.echoItSolution.user_service.enums.AuthProviderType;

import java.util.Objects;

public class UserUtil {

    public static AuthProviderType getProviderType(AuthProviderType providerType){
        return Objects.requireNonNullElse(providerType, AuthProviderType.EMAIL);
    }
}
