package com.gfa.green_buy.repository;

import com.gfa.green_buy.model.entity.SellableItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellableItemRepository extends JpaRepository<SellableItem,Long> {
    List<SellableItem> findAllBySoldIsFalse (Pageable pageable);
    SellableItem getSellableItemById (Long id);
}
