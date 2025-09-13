package com.echoItSolution.user_service.service;

import com.echoItSolution.user_service.dto.UserDTO;
import com.echoItSolution.user_service.dto.UserRequestDTO;
import com.echoItSolution.user_service.entity.Role;
import com.echoItSolution.user_service.entity.User;
import com.echoItSolution.user_service.enums.RoleType;
import com.echoItSolution.user_service.repository.UserRepository;
import com.echoItSolution.user_service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserDTO create(UserRequestDTO userRequestDTO) {
        User user = userRepository.findByUserName(userRequestDTO.getUserName()).orElse(null);

        if(user != null)
            throw new IllegalArgumentException("User already exist.");
        Role defaultRole = roleService.getByName(RoleType.USER);
        user = userRepository.save(
                User.builder().userName(userRequestDTO.getUserName())
                        .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                        .providerType(UserUtil.getProviderType(userRequestDTO.getProviderType()))
                        .providerId(userRequestDTO.getProviderId())
                        .userRoles(Set.of(defaultRole))
                        .build()
        );
        return new UserDTO(user.getId(), user.getUserName(), user.getUserRoles());
    }

}
