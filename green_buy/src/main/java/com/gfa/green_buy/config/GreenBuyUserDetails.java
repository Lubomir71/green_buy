//package com.gfa.green_buy.config;
//
//import com.gfa.green_buy.model.entity.User;
//import com.gfa.green_buy.repository.UserRepository;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//@Configuration
//public class GreenBuyUserDetails implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public GreenBuyUserDetails(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String userName = null;
//        String password = null;
//        List<GrantedAuthority> authorities = null;
//        User user =  userRepository.findUserByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User details not found for the user: " + username);
//        } else{
//            userName = user.getUsername();
//            password = user.getPassword();
//            authorities = new ArrayList<>();
//            authorities.add(new SimpleGrantedAuthority(user.getRole()));
//        }
//        return new org.springframework.security.core.userdetails.User(userName,password,authorities);
//    }
//}
