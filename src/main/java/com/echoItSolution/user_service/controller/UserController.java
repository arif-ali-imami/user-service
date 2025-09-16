package com.echoItSolution.user_service.controller;

import com.echoItSolution.user_service.dto.UserDTO;
import com.echoItSolution.user_service.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.echoItSolution.user_service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> signUp(
            @RequestBody UserRequestDTO userRequestDTO
    ){
        return ResponseEntity.ok(userService.create(userRequestDTO));
    }

    @GetMapping(value = "/get/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
