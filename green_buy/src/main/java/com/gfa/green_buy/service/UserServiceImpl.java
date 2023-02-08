package com.gfa.green_buy.service;

import com.gfa.green_buy.model.dto.LoginDTO;
import com.gfa.green_buy.model.entity.User;
import com.gfa.green_buy.repository.UserRepository;
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
    private final UserRepository userRepository;

    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public String createToken(LoginDTO loginDTO) {
        if (loginDTO.getUsername()==null) throw new IllegalArgumentException("Username is missing");
        if (loginDTO.getPassword()==null) throw new IllegalArgumentException("Password is missing");
        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword()));
        return generateToken(authentication);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    private String generateToken(Authentication authentication){
        String jwt="";
        if (authentication!=null){
            SecretKey key = Keys.hmacShaKeyFor(System.getenv("JWT_KEY").getBytes(StandardCharsets.UTF_8));
            jwt = Jwts.builder().setIssuer("GreenBuy").setSubject("JWT Token")
                    .claim("username",authentication.getName())
                    .claim("id",userRepository.findUserByUsername(authentication.getName()).getId())
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
