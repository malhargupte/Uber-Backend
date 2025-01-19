package com.guptem.UberBackend.controllers;

import com.guptem.UberBackend.dto.SignUpDto;
import com.guptem.UberBackend.dto.UserDto;
import com.guptem.UberBackend.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/signup")
    public UserDto signup(@RequestBody SignUpDto signUpDto) {

        return authService.signUp(signUpDto);

    }
}
