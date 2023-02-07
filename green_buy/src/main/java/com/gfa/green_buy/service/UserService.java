package com.gfa.green_buy.service;

import com.gfa.green_buy.model.dto.LoginDTO;

public interface UserService {

    String createToken(LoginDTO loginDTO);
}
