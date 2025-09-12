package com.echoItSolution.user_service.mapper;


import com.echoItSolution.user_service.dto.UserDTO;
import com.echoItSolution.user_service.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user){
        return UserDTO.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .userRoles(user.getUserRoles()).build();
    }
}
