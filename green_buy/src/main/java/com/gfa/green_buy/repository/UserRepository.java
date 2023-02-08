package com.gfa.green_buy.repository;

import com.gfa.green_buy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByUsername(String username);
    User getUserById (Long id);
}
