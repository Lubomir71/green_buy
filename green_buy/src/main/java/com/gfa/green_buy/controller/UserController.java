package com.gfa.green_buy.controller;

import com.gfa.green_buy.model.dto.LoginDTO;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> logUser(@RequestBody LoginDTO loginDTO) {
        Map<String, String> response = new HashMap();
        try{
            response.put("token", userService.createToken(loginDTO));
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            response.put("error",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/welcome")
    public String hello(){
        try{
            return "Welcome to application";
        }catch (Exception e){
            return e.getMessage();
        }

    }
}
