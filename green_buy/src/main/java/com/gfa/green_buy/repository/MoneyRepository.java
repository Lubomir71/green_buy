package com.gfa.green_buy.repository;

import com.gfa.green_buy.model.entity.Money;
import com.gfa.green_buy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoneyRepository extends JpaRepository<Money,Long> {
    Money findMoneyByUser(User user);
}
