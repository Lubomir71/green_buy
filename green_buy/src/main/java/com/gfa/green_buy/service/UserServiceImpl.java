package com.gfa.green_buy.service;

import com.gfa.green_buy.model.dto.LoginDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String createToken(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword()));
        return generateToken(authentication);
    }

    private String generateToken(Authentication authentication){
        String jwt="";
        if (authentication!=null){
            SecretKey key = Keys.hmacShaKeyFor(System.getenv("JWT_KEY").getBytes(StandardCharsets.UTF_8));
            jwt = Jwts.builder().setIssuer("GreenBuy").setSubject("JWT Token")
                    .claim("username",authentication.getName())
                    .claim("authorities",getAuthorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime()+30000000))
                    .signWith(key).compact();
        }
        return jwt;
    }

    private String getAuthorities (Collection<? extends GrantedAuthority> authorities){
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority:authorities){
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",",authoritiesSet);
    }
}