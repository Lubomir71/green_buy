package com.gfa.green_buy.service;

import com.gfa.green_buy.model.entity.User;
import com.gfa.green_buy.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class DecodeJWTImpl implements DecodeJWT {

    private final UserRepository userRepository;

    public DecodeJWTImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User decodeUser(String jwt) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(System.getenv("JWT_KEY").getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            String username = String.valueOf(claims.get("username"));
            return userRepository.findUserByUsername(username);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid Token!");
        }
    }
}
