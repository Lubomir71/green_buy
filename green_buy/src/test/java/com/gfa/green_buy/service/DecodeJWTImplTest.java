package com.gfa.green_buy.service;

import com.gfa.green_buy.model.entity.User;
import com.gfa.green_buy.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
class DecodeJWTImplTest {

    DecodeJWT decodeJWT;

    @Mock
    UserRepository userRepository;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        decodeJWT = new DecodeJWTImpl(userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void decodeUserError() {

        String jwt="jwt";
        Throwable ex = assertThrows(BadCredentialsException.class, ()-> decodeJWT.decodeUser(jwt));
        assertEquals("Invalid Token!",ex.getMessage());
    }
}