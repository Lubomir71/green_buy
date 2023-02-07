package com.gfa.green_buy.repository;

import com.gfa.green_buy.model.entity.Bid;
import com.gfa.green_buy.model.entity.SellableItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid,Long> {
    Bid findTopBySellableItemOrderByOfferDesc (SellableItem sellableItem);
}
