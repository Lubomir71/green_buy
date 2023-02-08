package com.gfa.green_buy.model.dto;

import com.gfa.green_buy.model.entity.SellableItem;

import java.util.ArrayList;
import java.util.List;

public class SellableItemDetailDTO extends SellableItemDTO{
    private String sellersName;
    private List<BidDTO> bids = new ArrayList<>();

    public SellableItemDetailDTO(){
    }

    public SellableItemDetailDTO(SellableItem sellableItem, List<BidDTO> bids) {
        super(sellableItem);
        this.sellersName=sellableItem.getUser().getUsername();
        this.bids = bids;
    }

    public String getSellersName() {
        return sellersName;
    }

    public void setSellersName(String sellersName) {
        this.sellersName = sellersName;
    }

    public List<BidDTO> getBids() {
        return bids;
    }

    public void setBids(List<BidDTO> bids) {
        this.bids = bids;
    }
}
