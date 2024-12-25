package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.SignUpDto;
import com.guptem.UberBackend.dto.UserDto;
import com.guptem.UberBackend.entities.Rider;
import com.guptem.UberBackend.entities.User;
import com.guptem.UberBackend.entities.enums.Role;
import com.guptem.UberBackend.exceptions.RuntimeConflictException;
import com.guptem.UberBackend.repo.UserRepo;
import com.guptem.UberBackend.services.AuthService;
import com.guptem.UberBackend.services.RiderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final RiderService riderService;

    public AuthServiceImpl(ModelMapper modelMapper, UserRepo userRepo, RiderService riderService) {
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.riderService = riderService;
    }

    @Override
    public UserDto signUp(SignUpDto signUpDto) {

        userRepo.findByEmail(signUpDto.getEmail())
                .orElseThrow(() -> new RuntimeConflictException("User with this email already exists!"));

        User mappedUser = modelMapper.map(signUpDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepo.save(mappedUser);

        //creating user related entities
        riderService.createNewRider(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public String login(String email, String password) {
        return "";
    }

    @Override
    public DriverDto onBoardNewDriver(Long userId) {
        return null;
    }
}
