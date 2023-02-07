package com.gfa.green_buy.repository;

import com.gfa.green_buy.model.entity.SellableItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellableItemRepository extends JpaRepository<SellableItem,Long> {

}
