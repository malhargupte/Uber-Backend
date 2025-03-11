package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.dto.SignUpDto;
import com.guptem.UberBackend.dto.UserDto;
import com.guptem.UberBackend.entities.User;
import com.guptem.UberBackend.entities.enums.Role;
import com.guptem.UberBackend.repo.UserRepo;
import com.guptem.UberBackend.security.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private RiderServiceImpl riderService;

    @Mock
    private WalletServiceImpl walletService;

    @Mock
    private DriverServiceImpl driverService;

    @Spy
    private ModelMapper modelMapper;

    @Spy
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("test");
        user.setRoles(Set.of(Role.RIDER));
    }

    @Test
    void testLogin_whenSuccess() {

        //Arrange
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtService.generateAccessToken(any(User.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        //Act
        String[] tokens = authService.login(user.getEmail(), user.getPassword());

        //Assert
        assertThat(tokens).hasSize(2);
        assertThat(tokens[0]).isEqualTo("accessToken");
        assertThat(tokens[1]).isEqualTo("refreshToken");

    }

    @Test
    void testSignUp_whenSuccess() {

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setEmail("test@gmail.com");
        signUpDto.setPassword("test");

        //Arrange
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepo.save(any(User.class))).thenReturn(user);

        //Act
        UserDto userDto = authService.signUp(signUpDto);

        //Assert
        assertThat(userDto).isNotNull();
        assertThat(userDto.getEmail()).isEqualTo(signUpDto.getEmail());
        verify(riderService).createNewRider(any(User.class));
        verify(walletService).createNewWallet(any(User.class));

    }

}
