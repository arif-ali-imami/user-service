package com.echoItSolution.user_service.service;

import com.echoItSolution.user_service.dto.UserDTO;
import com.echoItSolution.user_service.dto.UserRequestDTO;
import com.echoItSolution.user_service.entity.User;
import com.echoItSolution.user_service.enums.AuthProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.echoItSolution.user_service.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO create(UserRequestDTO userRequestDTO) {
        User user = userRepository.findByUserName(userRequestDTO.getUserName()).orElse(null);

        if(user != null)
            throw new IllegalArgumentException("User already exist.");
        user = userRepository.save(
                User.builder().userName(userRequestDTO.getUserName())
                        .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                        .providerType(AuthProviderType.EMAIL)
                        .build()
        );
        return new UserDTO(user.getId(), user.getUserName(), null);
    }

}
