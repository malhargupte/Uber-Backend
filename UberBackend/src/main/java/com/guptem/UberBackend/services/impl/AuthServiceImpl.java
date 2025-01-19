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
import com.guptem.UberBackend.services.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final RiderService riderService;
    private final WalletService walletService;

    public AuthServiceImpl(ModelMapper modelMapper, UserRepo userRepo, @Lazy RiderService riderService, WalletService walletService) {
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.riderService = riderService;
        this.walletService = walletService;
    }

    @Override
    @Transactional
    public UserDto signUp(SignUpDto signUpDto) {

        User user = userRepo.findByEmail(signUpDto.getEmail())
                .orElse(null);

        if(user != null) {
            throw new RuntimeConflictException("User already exists with this email!");
        }

        User mappedUser = modelMapper.map(signUpDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepo.save(mappedUser);

        //creating user related entities
        riderService.createNewRider(savedUser);

        walletService.createNewWallet(savedUser);

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
