package com.gfa.green_buy.model.dto;

import com.gfa.green_buy.model.entity.SellableItem;

import java.util.List;

public class SellableItemDetailBuyerDTO extends SellableItemDetailDTO{
    private String buyer;

    public SellableItemDetailBuyerDTO(SellableItem sellableItem, List<BidDTO> bids, String buyer) {
        super(sellableItem, bids);
        this.buyer = buyer;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
}
