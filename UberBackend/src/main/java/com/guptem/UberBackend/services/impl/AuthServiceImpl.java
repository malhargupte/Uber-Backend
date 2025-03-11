package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.SignUpDto;
import com.guptem.UberBackend.dto.UserDto;
import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.Rider;
import com.guptem.UberBackend.entities.User;
import com.guptem.UberBackend.entities.enums.Role;
import com.guptem.UberBackend.exceptions.ResourceNotFoundException;
import com.guptem.UberBackend.exceptions.RuntimeConflictException;
import com.guptem.UberBackend.repo.UserRepo;
import com.guptem.UberBackend.security.JWTService;
import com.guptem.UberBackend.services.AuthService;
import com.guptem.UberBackend.services.DriverService;
import com.guptem.UberBackend.services.RiderService;
import com.guptem.UberBackend.services.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.guptem.UberBackend.entities.enums.Role.DRIVER;

@Service
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthServiceImpl(ModelMapper modelMapper, UserRepo userRepo, @Lazy RiderService riderService, WalletService walletService, DriverService driverService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.riderService = riderService;
        this.walletService = walletService;
        this.driverService = driverService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepo.save(mappedUser);

        //creating user related entities
        riderService.createNewRider(savedUser);

        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public String[] login(String email, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken, refreshToken};

    }

    @Override
    public DriverDto onBoardNewDriver(Long userId, String vehicleId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if(user.getRoles().contains(DRIVER))
            throw new RuntimeConflictException("Already a driver with id: " + userId);


        Driver createDriver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();
        user.getRoles().add(DRIVER);
        userRepo.save(user);

        Driver savedDriver = driverService.createNewDriver(createDriver);

        return modelMapper.map(savedDriver, DriverDto.class);

    }

    @Override
    public String refreshToken(String refreshToken) {

        Long userId = jwtService.getUserIDFromToken(refreshToken);
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return jwtService.generateAccessToken(user);

    }
}
