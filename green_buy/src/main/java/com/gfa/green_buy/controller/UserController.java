package com.gfa.green_buy.controller;

import com.gfa.green_buy.model.dto.ErrorDTO;
import com.gfa.green_buy.model.dto.LoginDTO;
import com.gfa.green_buy.repository.MoneyRepository;
import com.gfa.green_buy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;
    private final MoneyRepository moneyRepository;

    public UserController(UserService userService, MoneyRepository moneyRepository) {
        this.userService = userService;
        this.moneyRepository = moneyRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> logUser(@RequestBody LoginDTO loginDTO) {
        Map<String,String> response = new HashMap<>();
        try{
            response.put("token", userService.createToken(loginDTO));
            response.put("dollars",moneyRepository.findMoneyByUser(userService.findUserByUsername(
                    loginDTO.getUsername())).getDollars().toString());
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
        }
    }
}
