package com.gfa.green_buy.service;

import com.gfa.green_buy.model.entity.User;

public interface DecodeJWT {
    User decodeUser (String token);
}
