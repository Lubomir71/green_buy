package com.gfa.green_buy.service;

import com.gfa.green_buy.model.dto.LoginDTO;
import com.gfa.green_buy.model.entity.User;

public interface UserService {

    String createToken(LoginDTO loginDTO);
    User findUserByUsername (String username);
}
