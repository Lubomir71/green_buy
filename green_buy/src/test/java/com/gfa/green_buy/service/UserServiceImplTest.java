package com.gfa.green_buy.service;

import com.gfa.green_buy.model.dto.LoginDTO;
import com.gfa.green_buy.model.entity.User;
import com.gfa.green_buy.repository.UserRepository;
import org.apache.juli.logging.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class UserServiceImplTest {

    UserService userService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserRepository userRepository;

    AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(authenticationManager,userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createTokenOK() {
        LoginDTO loginDTO = new LoginDTO("testUser","testPassword");

        when(authenticationManager.authenticate(any())).thenReturn(null);
        String jwt = userService.createToken(loginDTO);

        verify(authenticationManager, times(1)).authenticate(any());
        assertEquals("",jwt);
    }

    @Test
    void createTokenError() {
        LoginDTO loginDTO = new LoginDTO(null,"testPassword");

        Throwable ex = assertThrows(IllegalArgumentException.class,()-> userService.createToken(loginDTO));
        assertEquals("Username is missing",ex.getMessage());

        loginDTO.setPassword(null);
        loginDTO.setUsername("testUsername");

        ex = assertThrows(IllegalArgumentException.class,()-> userService.createToken(loginDTO));
        assertEquals("Password is missing",ex.getMessage());
    }

    @Test
    void findUserByUsername() {
        User user = new User("test", "12345", "user");
        when(userRepository.findUserByUsername(any())).thenReturn(user);

        User userResponse = userService.findUserByUsername(user.getUsername());

        verify(userRepository,times(1)).findUserByUsername(any());
        assertEquals(user,userResponse);
    }
}