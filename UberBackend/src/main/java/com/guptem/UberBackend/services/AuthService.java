package com.guptem.UberBackend.services;

import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.SignUpDto;
import com.guptem.UberBackend.dto.UserDto;

public interface AuthService {

    UserDto signUp(SignUpDto signUpDto);
    String login(String email, String password);
    DriverDto onBoardNewDriver(Long userId);

}
