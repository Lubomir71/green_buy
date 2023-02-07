package com.gfa.green_buy;

import com.gfa.green_buy.model.entity.User;
import com.gfa.green_buy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GreenBuyApplication implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public GreenBuyApplication(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(GreenBuyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count()==0) {
            userRepository.save(new User("lubo", passwordEncoder.encode("12345"), "admin"));
            userRepository.save(new User("john", passwordEncoder.encode("12345"), "user"));
            userRepository.save(new User("luky", passwordEncoder.encode("12345"), "user"));
            userRepository.save(new User("jimi", passwordEncoder.encode("12345"), "user"));
            userRepository.save(new User("alice", passwordEncoder.encode("12345"), "user"));
        }
    }
}
